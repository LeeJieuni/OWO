<template>
  <div class="calendar">
    <div class="month-title container-fluid row p-0 m-0">
      <div class="col-2">
        <a class="month-change-btn" href="#"
          @click.prevent="onClickPrev(currentMonth)">◀</a>
      </div>
      <div class="col-8">
        <h2 class="date-title p-0 m-0">{{currentYear}}년 {{currentMonth}}월</h2>
      </div>
      <div class="col-2">
        <a class="month-change-btn" href="#" v-on:click.prevent="onClickNext(currentMonth)">▶</a>
      </div>
    </div>
    <div class="calendar-table">
      <table class="table table-hover">
        <thead>
          <tr>
            <td class="weekNames" v-for="(weekName, index) in weekNames" v-bind:key="index">
              {{weekName}}
            </td>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(row, index) in currentCalendarMatrix" :key="index">
            <td class="day-td" v-for="(day, index2) in row" :key="index2">
              <a v-if="isToday(currentYear, currentMonth, day)" class="today day-a" href="#"
                @click="selectDay(currentYear, currentMonth, day)" v-b-modal="'myModal'">
                {{day}}
              </a>
              <!-- <a>
                <img class="stamp" src="@/assets/icon/stamps/evaluate1.png" alt=""></img>
              </a> -->
              <span v-else>
                <a class="days day-a" href="#" @click="selectDay(currentYear, currentMonth, day)"
                  v-b-modal="'myModal'">{{day}}</a>
              </span>
              <img v-if="day"
              class="dot" src="@/assets/icon/dot.png" alt="">
            </td>
          </tr>
        </tbody>
      </table>

      <router-link to="/slide">변화 한 눈에 보기</router-link>

      <b-modal id="myModal" size="lg" button-size="sm" scrollable ref="myModal"
      class="myModal" hide-footer
        :title="`${currentYear}년 ${currentMonth}월 ${day}일`" hide-header>
        <div class="carousel-box">
          <div class="month-title modal-title text-center container-fluid row p-0 m-0 mb-3">
            <div class="col-2">
              <a class="day-change-btn" href="#"
                @click.prevent="onClickPrevDay(currentYear, currentMonth, day)">◀</a>
            </div>
            <div class="col-8">
              <h1 class="date-title p-0 m-0">{{currentYear}}년 {{currentMonth}}월 {{day}}일</h1>
            </div>
            <div class="col-2">
              <a class="day-change-btn" href="#"
                v-on:click.prevent="onClickNextDay(currentYear, currentMonth, day)">▶</a>
            </div>
          </div>
          <!--카로셀-->
          <div class="row">
            <div class="no-record-day mt-5" v-if="dayExerciseList = []">이 날은 운동 기록이 없네요!</div>
            <!-- {{ dayExerciseList }} -->
            <!-- <img :src="imageUrl" alt=""> -->
            <div class="p-0 m-0 mt-5 px-5" v-for="(exercise, i) in dayExerciseList" :key="i">
              <div class="row record-number">
                <span>
                <!--eslint-disable-next-line-->
                  <span class="record-idx">{{ i + 1 }}</span><span class="record-exercise">{{ exercise.exercise }}</span>
                </span>
              </div>
              <!-- recordId: {{ exercise.recordId }} <br> -->
              <!-- memberId: {{ exercise.memberId }} <br> -->
              <!-- meetingRoomId: {{ exercise.meetingRoomId }} <br> -->

              <div class="picture-wrapper d-flex justify-content-center mb-3">
                <img class="picture" :src="dayPictures[i].fileUrl" alt="">
              </div>

              <!--태그-->
              <!-- tags: {{ exercise.tags }} <br> -->
              <div class="tags d-flex justify-content-center">
                <div class="d-flex justify-content-start" style="width: 400px;">
                  <button
                    v-for="(tag, tagI) in exercise.tags"
                    :key="tagI"
                    class="tag ">
                    <p class="tag-name"># {{tag.tagContent}}</p>
                  </button>
                </div>
              </div>

              <div class=" d-flex justify-content-center">
                <div class="d-flex justify-content-between" style="width: 400px;">
                  <div>
                    <p class="record-exercise">{{ exercise.exercise }}</p>
                  </div>
                  <div>
                    <p class="record-min">{{ exercise.recordHour }}분</p>
                  </div>
                </div>
              </div>

              <div class=" d-flex justify-content-center">
                <div class="d-flex justify-content-between">
                  <div class="memo-box">
                    <p class="memo">
                      {{ exercise.recordMemo }}
                    </p>
                  </div>
                </div>
              </div>
              <!-- recordSecret: {{ exercise.recordSecret }} <br> -->
              <hr>
            </div>
          </div>
        </div>

        <!-- <template #modal-footer="{}">
          <b-button size="sm"
            class="mt-3 modal-close" variant="outline-danger" block>
            Cancel
          </b-button>
        </template> -->
      </b-modal>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';

export default {
  name: 'MyCalendar',
  data() {
    return {
      weekNames: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
      rootYear: 1904,
      rootDayOfWeekIndex: 4, // 2000년 1월 1일은 토요일
      currentYear: new Date().getFullYear(),
      currentMonth: new Date().getMonth() + 1,
      currentDay: new Date().getDate(),
      stringDate: '',
      currentMonthStartWeekIndex: null,
      currentCalendarMatrix: [],
      endOfDay: null,
      // memoDatas: [],
      // day: null,
      // tags: ['오운완', '상체', '등', '어깨', '복근'],
      // exerciseType: '헬스',
    };
  },
  computed: {
    ...mapGetters('record', ['dayExerciseList', 'dayPictures']),
    ...mapGetters('accounts', ['monthRecord']),
  },
  mounted() {
    this.init();
  },
  methods: {
    ...mapActions('record', ['fetchDayExerciseList', 'fetchDayPictures']),
    ...mapActions('accounts', ['fetchMonthRecord']),
    hideModal() {
      console.log(this.$refs.myModal);
      this.$refs.myModal.hide();
    },
    init() {
      this.currentMonthStartWeekIndex = this.getStartWeek(this.currentYear, this.currentMonth);
      this.endOfDay = this.getEndOfDay(this.currentYear, this.currentMonth);
      this.initCalendar();
    },
    initCalendar() {
      this.currentCalendarMatrix = [];
      let day = 1;
      for (let i = 0; i < 6; i += 1) {
        const calendarRow = [];
        for (let j = 0; j < 7; j += 1) {
          if (i === 0 && j < this.currentMonthStartWeekIndex) {
            calendarRow.push('');
          } else if (day <= this.endOfDay) {
            calendarRow.push(day);
            day += 1;
          } else {
            calendarRow.push('');
          }
        }
        this.currentCalendarMatrix.push(calendarRow);
      }
    },
    getEndOfDay(year, month) {
      switch (month) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
          return 31;
          // break;
        case 4:
        case 6:
        case 9:
        case 11:
          return 30;
          // break;
        case 2:
          if (((year % 4 === 0) && (year % 100 !== 0)) || (year % 400 === 0)) {
            return 29;
          }
          return 28;
          // break;
        default:
          console.log(`unknown month ${month}`);
          return 0;
          // break;
      }
    },
    getStartWeek(targetYear, targetMonth) {
      let year = this.rootYear;
      let month = 1;
      let sumOfDay = this.rootDayOfWeekIndex;
      while (true) {
        if (targetYear > year) {
          for (let i = 0; i < 12; i += 1) {
            sumOfDay += this.getEndOfDay(year, month + i);
          }
          year += 1;
        } else if (targetYear === year) {
          if (targetMonth > month) {
            sumOfDay += this.getEndOfDay(year, month);
            month += 1;
          } else if (targetMonth === month) {
            return (sumOfDay) % 7;
          }
        }
      }
    },
    // 캘린더에서 월 변경
    onClickPrev(month) {
      this.month -= 1;
      if (month <= 0) {
        this.currentMonth = 12;
        this.currentYear -= 1;
      } else {
        this.currentMonth -= 1;
      }
      this.init();
      const payload = [this.currentYear, this.currentMonth];
      console.log(payload);
      // this.fetchMonthRecord(payload);
    },
    onClickNext(month) {
      this.month += 1;
      if (month > 12) {
        this.currentMonth = 1;
        this.currentYear += 1;
      } else {
        this.currentMonth += 1;
      }
      this.init();
    },
    // 모달 내에서 날짜 변경
    onClickPrevDay(year, month, day) {
      const date = new Date(year, month - 1, day);
      date.setDate(date.getDate() - 1);
      this.currentYear = date.getFullYear();
      this.currentMonth = date.getMonth() + 1;
      this.day = date.getDate();
      // axios로 보낼 문자열 날짜 (예.20220820)
      const stringYear = String(this.currentYear);
      let stringMonth;
      if (this.currentMonth >= 10) {
        stringMonth = String(this.currentMonth);
      } else {
        stringMonth = String(0) + String(this.currentMonth);
      }
      let stringDay;
      if (this.day >= 10) {
        stringDay = String(this.day);
      } else {
        stringDay = String(0) + String(this.day);
      }
      this.stringDate = stringYear + stringMonth + stringDay;
      // console.log(stringMonth);
      // console.log(stringDay);
      // console.log(this.stringDate);
      this.fetchDayExerciseList(this.stringDate);
      this.fetchDayPictures(this.stringDate);
    },
    onClickNextDay(year, month, day) {
      const date = new Date(year, month - 1, day);
      date.setDate(date.getDate() + 1);
      this.currentYear = date.getFullYear();
      this.currentMonth = date.getMonth() + 1;
      this.day = date.getDate();
      // axios로 보낼 문자열 날짜 (예.20220820)
      const stringYear = String(this.currentYear);
      let stringMonth;
      if (this.currentMonth >= 10) {
        stringMonth = String(this.currentMonth);
      } else {
        stringMonth = String(0) + String(this.currentMonth);
      }
      let stringDay;
      if (this.day >= 10) {
        stringDay = String(this.day);
      } else {
        stringDay = String(0) + String(this.day);
      }
      this.stringDate = stringYear + stringMonth + stringDay;
      this.fetchDayExerciseList(this.stringDate);
      this.fetchDayPictures(this.stringDate);
    },

    isToday(year, month, day) {
      const date = new Date();
      return year === date.getFullYear() && month === date.getMonth() + 1 && day === date.getDate();
    },
    selectDay(year, month, day) {
      this.currentYear = year;
      this.currentMonth = month;
      this.day = day;
      const stringYear = String(this.currentYear);
      let stringMonth;
      if (this.currentMonth >= 10) {
        stringMonth = String(this.currentMonth);
      } else {
        stringMonth = String(0) + String(this.currentMonth);
      }
      let stringDay;
      if (this.day >= 10) {
        stringDay = String(this.day);
      } else {
        stringDay = String(0) + String(this.day);
      }
      this.stringDate = stringYear + stringMonth + stringDay;
      this.fetchDayExerciseList(this.stringDate);
      this.fetchDayPictures(this.stringDate);
    },
    cancle() {
      const modal = document.querySelector('.modal');
      modal('hide');
    },
  },
  // setup() {
  //   const store = useStore();
  //   const dayExerciseList = computed(() => store.getters['accounts/dayExerciseList']);

  //   // 모달 내에서 날짜 변경
  //   const onClickPrevDay = function (year, month, day) {
  //     const date = new Date(year, month - 1, day);
  //     date.setDate(date.getDate() - 1);
  //     this.currentYear = date.getFullYear();
  //     this.currentMonth = date.getMonth() + 1;
  //     this.day = date.getDate();
  //     // axios로 보낼 문자열 날짜 (예.20220820)
  //     const stringYear = String(this.currentYear);
  //     let stringMonth;
  //     if (this.currentMonth >= 10) {
  //       stringMonth = String(this.currentMonth);
  //     } else {
  //       stringMonth = String(0) + String(this.currentMonth);
  //     }
  //     let stringDay;
  //     if (this.currentDay >= 10) {
  //       stringDay = String(this.day);
  //     } else {
  //       stringDay = String(0) + String(this.day);
  //     }
  //     this.stringDate = stringYear + stringMonth + stringDay;
  //     console.log(this.stringDate);
  //     store.dispatch('record/fetchDayExerciseList', this.stringDate);
  //   };
  //   const onClickNextDay = function (year, month, day) {
  //     const date = new Date(year, month - 1, day);
  //     date.setDate(date.getDate() + 1);
  //     this.currentYear = date.getFullYear();
  //     this.currentMonth = date.getMonth() + 1;
  //     this.day = date.getDate();
  //     // axios로 보낼 문자열 날짜 (예.20220820)
  //     const stringYear = String(this.currentYear);
  //     let stringMonth;
  //     if (this.currentMonth >= 10) {
  //       stringMonth = String(this.currentMonth);
  //     } else {
  //       stringMonth = String(0) + String(this.currentMonth);
  //     }
  //     let stringDay;
  //     if (this.currentDay >= 10) {
  //       stringDay = String(this.day);
  //     } else {
  //       stringDay = String(0) + String(this.day);
  //     }
  //     this.stringDate = stringYear + stringMonth + stringDay;
  //     console.log(this.stringDate);
  //   };
  //   return {
  //     // state,
  //     dayExerciseList,
  //     onClickPrevDay,
  //     onClickNextDay,
  //   };
  // },
};
</script>

<style scoped>
.calendar {
  color: #2E2E2E;
  max-width: 610px;
}
.my-calendar-title {
  text-align: left;
  padding-top: 50px;
  font-size: 22px;
  padding-bottom: 20px;
  padding-left: 50px;
}
.date-title {
  font-family: 'Black Han Sans', sans-serif;
}

.calendar-table {
  margin-left: 50px;
  margin-right: 50px;
}
.month-change-btn {
  text-decoration: none;
  font-size: 24px;
  color: gray;
}
.month-change-btn:hover {
  color: #4E8AFF;
}
.day-change-btn {
  text-decoration: none;
  font-size: 28px;
  color: gray;
}
.day-change-btn:hover {
  color: #4E8AFF;
}
.days {
  text-decoration: none;
  color: black;
  padding: 5px;
}
.today {
  color: #4E8AFF;
  text-decoration: none;
}
.carousel-box {
  margin: 50px;
}

/* 캐러셀(이미지슬라이드) 이미지 크기변경 */
.carousel-inner{
  width:auto;
  height:400px; /* 이미지 높이 변경 */
}
.carousel-item{
  width: auto;
  height:100%;
}
.d-block {
  display:block;
  width: auto;
  height: 100%;
}
.carousel {
  display: flex;
  /* padding: 50px; */
}
.exercise-type-box {
  margin-bottom: 10px;
}
.modalImg {
  display: flex;
  /* justify-content: center; */
  margin: 0 auto;
  Background-size : cover;
}
.record-number {
  margin-left: 80px;
}
.record-idx {
  font-size: 28px;
  font-weight: 900;
  margin-right: 20px;
}
.tags {
  width: 100%;
  /* padding: 10px; */
  margin-bottom: 10px;
}
.tag {
  width: 80px;
  height: 26px;
  border: solid #828282 0px;
  display:inline-block;
  border-radius: 3px;
  padding: .2em .5em .3em;
  font-weight: 600;
  margin: .25em .1em;
  font-size: 12px;
  font-weight: 700;
  background-color:#393939;
  /* padding-left: 10px; */
  line-height: 25px;
  color: white;
  letter-spacing: -1.5;
  text-align: center;
  /* box-shadow: 3px 3px #898e97; */
}
.tag-name {
  text-align: center;
}
.tag:hover {
  background-color: #4E8AFF;
  color: white;
  transition: 0.2s;
  cursor: pointer;
}
.record-min {
  font-size: 12px;
  font-weight: 400;
}
.record-exercise {
  font-size: 15px;
  font-weight: 900;
}
.stamp-div {
}
.stamp{
  /* margin: 0 auto; */
  /* margin-top: -5px; */
  width: 40px;
}
.day-a {
  font-size: 20px;
  font-weight: 900;
  font-family: 'Righteous', cursive;
  padding: 0;
  margin: 0;
}
.dot{
  padding: 0 0 30px 0;
}

.table {
  /* --bs-table-hover-bg: rgba(75, 172, 237, 0.08); */
}
.day-td  {
  --bs-table-hover-bg: rgba(0, 6, 10, 0.08);
  padding: 20px;
}
td {
  /* border: 0.5px solid #c5c5c5; */
  /* text-align: left; */
  height: 70px;
  width: 70px;
  /* padding: 20px; */
}
.table > a {
  text-decoration: none;
  font-size: 20px;
  /* padding: 20px; */
  /* text-align: left; */
}
.weekNames {
  font-size: 24px;
  font-weight: 900;
  text-align: center;
  align-items: center;
  line-height: 40px;
  height: 10px;
  font-family: 'Black Han Sans', sans-serif;
}

/* 모달 스타일링 */
.modal-dialog-scrollable .modal-body::-webkit-scrollbar {
  width: 10px;
  background-color: black;
}
.myModal a, .myModal p:not(.memo) {
  font-family: 'NanumSquareRound';
}
.picture-wrapper {
  position: relative;
  width: 400px;
  height: 277px;
  margin: 0 auto;
}
.picture-wrapper img {
  /* border-radius: 50%; */
  /* margin: 0 auto; */
  position: absolute;
  top: 0;
  left: 0;
  transform: translate(50, 50);
  width: 100%;
  height: 100%;
  object-fit: cover;
  margin: auto;
  border-radius: 15px;
  border: 1px solid #DFDFDF;
  box-shadow: 3px 3px 3px #DFDFDF;
}
.lg-title {
  font-weight: 900;
  font-size: 30px;
  text-align: center;
  padding-bottom: 20px;
}
.memo-box {
  border: solid #DFDFDF 1px;
  box-shadow: 1px 1px 2px #DFDFDF;
  margin: 20px;
  padding: 20px;
  margin-bottom: 50px;
  border-radius: 10px;
  width: 400px;
  /* color: #2E2E2E; */
}
.memo {
  text-align: justify;
  font-family: 'Fairytale_ddobak';
  font-weight: 400;
  font-size: 20px;
  letter-spacing: -1.2;
  margin-bottom: 4px;
  /* color: #2E2E2E; */
}
.no-record-day {
  font-weight: 700;
  text-align: center;
}
</style>
