<template>
  <div class="calendar">
    <div class="month-title">
      <h2>
        <a class="month-change-btn" href="#"
          @click.prevent="onClickPrev(currentMonth)">◀</a>
          <span class="date-title">{{currentYear}}년 {{currentMonth}}월</span>
        <a class="month-change-btn" href="#" v-on:click.prevent="onClickNext(currentMonth)">▶</a>
      </h2>
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
            </td>
          </tr>
        </tbody>
      </table>

      <router-link to="/slide">변화 한 눈에 보기</router-link>

      <b-modal id="myModal" size="lg" button-size="sm" scrollable ref="myModal"
      class="myModal"
        :title="`${currentYear}년 ${currentMonth}월 ${day}일`" hide-header>
        <div class="carousel-box">
          <div class="modal-title">
            <a class="month-change-btn" href="#"
              @click.prevent="onClickPrevDay(currentYear, currentMonth, day)">◀</a>
              <span class="lg-title">{{currentYear}}년 {{currentMonth}}월 {{day}}일 운동 기록</span>
            <a class="month-change-btn" href="#"
              v-on:click.prevent="onClickNextDay(currentYear, currentMonth, day)">▶</a>
          </div>
          <!--카로셀-->
          <div>
            <img :src="recordPicture" alt="">
          </div>

          <div class="row">
            <div v-for="(exercise, i) in dayExerciseList" :key="i">
              recordId: {{ exercise.recordId }} <br>
              memberId: {{ exercise.memberId }} <br>
              meetingRoomId: {{ exercise.meetingRoomId }} <br>
              tags: {{ exercise.tags }} <br>
              recordHour: {{ exercise.recordHour }} <br>
              recordMemo: {{ exercise.recordMemo }} <br>
              datetime: {{ exercise.datetime }} <br>
              recordSecret: {{ exercise.recordSecret }} <br>
              exercise: {{ exercise.exercise }} <hr>
            </div>
          </div>

          <!--태그-->
          <div class="tags row">
            <button
              v-for="(tag, tagI) in tags"
              :key="tagI"
              class="tag"
            >
              <p class="tag-name"># {{tag}}</p>
            </button>
          </div>

          <!--운동 종류-->
          <div class="exercise-type-box">
            <p>{{ exerciseType }}</p>
          </div>

          <!--메모-->
          <div class="memo-box">
            <p class="memo">
              사레레 10*20*3 , 밀리터리 프레스 10*20*3 , 프레 10*20*3 , 벤치프레스 10*20*3 , 스쿼트 40*20*3 ,
              레그레이즈 20*3 함. 힘들다.. 그래도 친구들이랑 같이 해서 재밌었다!
            </p>
          </div>

          <!--함께 운동한 사람-->
          <div class="people-list">
            <p class="people-title">함께 운동한 사람</p>
            <div class="d-flex container-fluid people-img-div justify-content-evenly">
              <img v-for="(profile, recordI) in record.profiles"
                :key="recordI" src="https://picsum.photos/50" alt="" class="people-img">
            </div>
          </div>
        </div>

        <div class="btns">
          <b-button
            class="mt-3 modal-close"
            variant="outline-danger" block
            @click.prevent="hideModal">
            Close Me
          </b-button>
        </div>

        <template #modal-footer="{}">
          <b-button size="sm"
            class="mt-3 modal-close" variant="outline-danger" block>
            Cancel
          </b-button>
        </template>
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
      weekNames: ['월', '화', '수', '목', '금', '토', '일'],
      rootYear: 1904,
      rootDayOfWeekIndex: 4, // 2000년 1월 1일은 토요일
      currentYear: new Date().getFullYear(),
      currentMonth: new Date().getMonth() + 1,
      currentDay: new Date().getDate(),
      stringDate: '',
      currentMonthStartWeekIndex: null,
      currentCalendarMatrix: [],
      endOfDay: null,
      memoDatas: [],
      day: null,
      tags: ['오운완', '상체', '등', '어깨', '복근'],
      exerciseType: '헬스',
      record: {
        profiles: [
          'https://picsum.photos/50', 'https://picsum.photos/50', 'https://picsum.photos/50', 'https://picsum.photos/50', 'https://picsum.photos/50', 'https://picsum.photos/50',
        ],
      },
      memo: '사레레 10*20*3 , 사레레 10*20*3 , 사레레 10*20*3 , 사레레 10*20*3 , 사레레 10*20*3 , 사레레 10*20*3 함. 개힘들었다 정말... 내일도 해야하는데 정말 미쳐버리겠다 운동을 좋아서 하는 사람들은 대체 어떤 사람',
      // dayExerciseList: '',
    };
  },
  computed: {
    ...mapGetters('record', ['dayExerciseList']),
  },
  mounted() {
    this.init();
  },
  methods: {
    ...mapActions('record', ['fetchDayExerciseList']),
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
.calendar {
  max-width: 600px;
}
.calendar-table {
  margin-left: 50px;
  margin-right: 50px;
}
.month-change-btn {
  text-decoration: none;
  font-size: 18px;
  color: gray;
}
.month-change-btn:hover {
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
.rounded {
  -moz-border-radius:20px 20px 20px 20px;
  border-radius:20px 20px 20px 20px;
  /* border:solid 1px #ffffff; */
  background-color:#4E8AFF;
  padding: 3px 6px;
  color:#ffffff;
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
.tags {
  padding: 10px;
  margin-bottom: 50px;
}
.tag {
  width: 80px;
  height: 26px;
  border: solid #828282 0px;
  display:inline-block;
  border-radius: 10px;
  /* padding: 4px; */
  margin: 2px;
  font-size: 12px;
  font-weight: 700;
  background-color:#4E8AFF;
  /* padding-left: 10px; */
  line-height: 25px;
  color: white;
  letter-spacing: -1.5;
  text-align: center;
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
}

.tag-name {
  text-align: center;
}
.tag:hover {
  background-color: #DE7474;
  color: white;
  transition: 0.2s;
  cursor: pointer;

}
.tag p {
  /* text-align: left; */
}
.table {
  /* --bs-table-hover-bg: rgba(75, 172, 237, 0.08); */
}
.day-td  {
  --bs-table-hover-bg: rgba(0, 6, 10, 0.08);
  padding: 20px;
}
td {
  border: 0.5px solid #c5c5c5;
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
.myModal a, .myModal p:not(.memo) {
  font-family: 'NanumSquareRound';
}
.lg-title {
  font-weight: 900;
  font-size: 30px;
  text-align: center;
  padding-bottom: 20px;
}

.people-title, .exercise-type-box {
  text-align: center;
  font-size: 18px;
  font-weight: 700;
}
.people-img {
  border-radius: 50%;
}
.memo-box {
  border: solid black 1px;
  margin: 20px;
  padding: 20px;
  margin-bottom: 50px;
  border-radius: 10px;
}
.memo {
  text-align: justify;
  font-family: 'Fairytale_ddobak';
  font-weight: 600;
  font-size: 20px;
  /* letter-spacing: 2; */
}
</style>
