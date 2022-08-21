<template>
  <div class="calendar">
    <div class="month-title container-fluid row p-0 m-0">
      <div class="col-2">
        <a class="month-change-btn" href="#"
          @click.prevent="onClickPrev(currentMonth)">◀</a>
      </div>
      <div class="col-8">
        <h2 class="out-date-title p-0 m-0">{{currentYear}}년 {{currentMonth}}월</h2>
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
              <img v-if="stampChk(day)"
              class="dot" src="@/assets/icon/dot.png" alt="">
            </td>
          </tr>
        </tbody>
      </table>
      <b-modal id="myModal" size="lg" button-size="sm" scrollable ref="myModal"
      class="myModal" hide-footer hide-header>
        <div class="modal-box">

          <div class="modal-title text-center d-flex align-items-center row p-0 m-0">
            <div class="col-2 align-items-center">
              <!-- eslint-disable-next-line-->
              <div class="arrow-div p-0 m-0" @click.prevent="onClickPrevDay(currentYear, currentMonth, day)">
                <img class="arrows" src="@/assets/icon/arrow-left-blue.png" alt="">
              </div>
            </div>
            <div class="col-8 align-items-center">
              <h3 class="date-title p-0 m-0">{{currentYear}}년 {{currentMonth}}월 {{day}}일</h3>
            </div>
            <div class="col-2 align-items-center">
              <!-- eslint-disable-next-line-->
              <div class="arrow-div p-0 m-0" @click.prevent="onClickNextDay(currentYear, currentMonth, day)">
                <img class="arrows" src="@/assets/icon/arrow-right-blue.png" alt="">
              </div>
            </div>
          </div>

          <div class="row">
            <div class="no-record-day mt-5" v-if="dayExerciseList.length <= 0">
              <p>이 날은 운동 기록이 없네요!</p>
            </div>

            <div class="p-0 m-0 mt-5 px-4" v-for="(exercise, i) in dayExerciseList" :key="i">
              <div class="d-flex justify-content-center">
                <div class="row">
                  <p class="record-idx">{{this.indexes[ i ]}} 번째 운동</p>
                </div>
              </div>

              <div class="wrapper ">

                <!--태그-->
                <!-- tags: {{ exercise.tags }} <br> -->
                <!-- <div class="test-tags d-flex justify-content-center">
                  <div class="d-flex justify-content-start">
                    <button
                      v-for="(tag, tagI) in exercise.tags"
                      :key="tagI"
                      class="tag ">
                      <p class="tag-name">{{tag.tagContent}}</p>
                    </button>
                  </div>
                </div> -->

              <div class="test-block">
                <div class="tags d-flex justify-content-center">
                  <div class="d-flex justify-content-start" style="width: 500px;">
                    <button
                      v-for="(tag, tagI) in exercise.tags"
                      :key="tagI"
                      class="test-tag ">
                      <p class="tag-name">{{tag.tagContent}}</p>
                    </button>
                  </div>
                </div>
              </div>
              <div class="test">
                <div class="picture-wrapper d-flex justify-content-center mb-3">
                  <img class="picture" :src="dayPictures[i]" alt="">
                </div>
              </div>

                <div class="d-flex justify-content-between">
                  <div class="row">
                    <p class="record-exercise">{{ exercise.exercise }}</p>
                  </div>
                  <div>
                    <p class="record-min">{{ exercise.recordHour }}분</p>
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
              </div>

              <!-- recordSecret: {{ exercise.recordSecret }} <br> -->
              <hr class="hr">
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
// import ModalNav from '@/components/MyPage/ModalNav.vue';

export default {
  name: 'MyCalendar',
  // components: { ModalNav },
  data() {
    return {
      weekNames: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
      indexes: ['첫', '두', '세', '네', '다섯', '여섯', '일곱', '여덟', '아홉', '열', '열 한'],
      rootYear: 1904,
      rootDayOfWeekIndex: 4, // 2000년 1월 1일은 토요일
      currentYear: new Date().getFullYear(),
      currentMonth: new Date().getMonth() + 1,
      currentDay: new Date().getDate(),
      stringDate: '',
      currentMonthStartWeekIndex: null,
      currentCalendarMatrix: [],
      endOfDay: null,
      day: '',
      stampDays: [],
      // memoDatas: [],
      // day: null,
      // tags: ['오운완', '상체', '등', '어깨', '복근'],
      // exerciseType: '헬스',
    };
  },
  computed: {
    ...mapGetters('record', ['dayExerciseList', 'dayPictures', 'monthStampHour', 'monthRecord']),
    // ...mapGetters('accounts', ['monthRecord']),
    // ...mapGetters('accounts', ['monthRecord']),
  },
  created() {
    const payload = [this.currentYear, this.currentMonth];
    this.fetchMonthStampHour(payload);
  },
  mounted() {
    this.init();
  },
  methods: {
    ...mapActions('record', ['fetchDayExerciseList', 'fetchDayPictures', 'fetchMonthStampHour', 'fetchSessions', 'fetchMonthRecord']),
    // ...mapActions('accounts', ['fetchMonthRecord']),
    // ...mapActions('accounts', ['fetchMonthRecord']),
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
      this.fetchMonthRecord(payload);
      this.fetchMonthStampHour(payload);
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
      const payload = [this.currentYear, this.currentMonth];
      console.log(payload);
      this.fetchMonthRecord(payload);
      // if (this.monthRecord.length !== 0) {
      //   this.fetchMonthStampHour(payload);
      // }
      this.fetchMonthRecord(payload);
      this.fetchMonthStampHour(payload);
    },
    // eslint-disable-next-line
    stampChk(d) {
      // const endDay = this.getEndOfDay(this.currentYear, this.currentMonth);
      // for (let i = 0; i < endDay; i += 1) {
      // console.log('month record');
      console.log('length check', this.monthRecord.length);
      if (this.monthRecord.lenght !== 0) {
        for (let i = 0; i < this.monthStampHour.length; i += 1) {
          if (this.monthStampHour[i].day === d) {
            console.log('true');
            return true;
          }
        }
      } else {
        return false;
      }
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
};
</script>

<style scoped>
.container-fluid {
  height: 100%;
}
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
.out-date-title {
  font-family: 'LeferiPoint-BlackObliqueA';
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
.modal-box {
  margin: 10px 50px;
  padding: 0;
  min-height: 500px;
  /* background-color:rgb(226, 234, 239); */
}

/* 캐러셀(이미지슬라이드) 이미지 크기변경 */
.myModal p:not(.record-idx){
  font-family: 'LeferiPoint-WhiteObliqueA';
}
.record-idx {
  font-size: 24px;
  font-weight: 900;
  margin-right: 20px;
  font-family: 'LeferiPoint-BlackObliqueA';
  /* background-color: #4E8AFF; */
  color: #4E8AFF;
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
  background-color: #4E8AFF;
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
  background-color:#393939;
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
  font-family: 'LeferiPoint-WhiteObliqueA';
  padding: 0;
  margin: 0;
}
.dot{
  padding: 0 0 30px 0;
}

.table {
  margin-top: 20px;
}
.day-td  {
  --bs-table-hover-bg: rgba(0, 6, 10, 0.08);
  padding: 20px;
}
td {
  /* border: 0.5px solid #c5c5c5; */
  /* text-align: left; */
  height: 70px;
  min-width: 70px;
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
  font-family: 'LeferiPoint-BlackObliqueA';
}

/* 모달 스타일링 */
/* .modal-body {
  padding: 0;
} */
/* .modal-dialog-scrollable .modal-body::-webkit-scrollbar {
  width: 10px;
  background-color: black;
} */
.date-title {
  /* line-height: 60px; */
  font-family: 'LeferiPoint-BlackObliqueA';
}

.modal-title {
  height: 55px;
  width: 100%;
  /* background-color: #d6dbe83b; */
  color: #4E8AFF;
}
.arrows {
  width: 30px;
}
.arrows:hover {
  top: -10px;
  cursor: url('@/assets/cursor/cursor-hover.cur'), auto;
}
.arrow-div:hover {
  top: -10px;
  cursor: url('@/assets/cursor/cursor-hover.cur'), auto;
}
.wrapper {
  width: 500px;
  margin: 0 auto;
}
.picture-wrapper {
  position: relative;
  width: 500px;
  height: 346px;
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
  border-radius: 10px;
  border: 1px solid #DFDFDF;
  box-shadow: 3px 3px 3px #474747;
}
.test-block{
  display: hidden;
  position: relative;
  width: 500px;
  height: 346px;
  margin: 0 auto;
  margin-bottom: -346px;
  z-index: 5;
  border-radius: 10px;
}
.test-block > .tags {
  display: none;
}
.test-tag {
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
  background-color: #4E8AFF;
  line-height: 25px;
  color: white;
  letter-spacing: -1.5;
  text-align: center;
  display: none;
}
.test-block:hover {
  display:block;
  background: linear-gradient(rgba(60, 110, 247, 0.458), rgba(51, 126, 255, 0));
}
.test-block:hover .test-tag {
  display:block;
}
.lg-title {
  font-weight: 900;
  font-size: 30px;
  text-align: center;
  padding-bottom: 20px;
}
.memo-box {
  border: solid #DFDFDF 1px;
  box-shadow: 3px 3px 3px #474747;
  margin: 20px;
  padding: 20px;
  margin-bottom: 50px;
  border-radius: 10px;
  width: 500px;
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
  min-height: 50px;
}
.no-record-day {
  font-weight: 700;
  text-align: center;
}
.hr {
  /* margin: 50px; */
}
</style>
