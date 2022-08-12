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
          <div class="modal-title text-center">
            <a class="month-change-btn" href="#"
              @click.prevent="onClickPrevDay(currentYear, currentMonth, day)">◀</a>
              <span class="lg-title">
                {{currentYear}}년 {{currentMonth}}월 {{day}}일 운동 기록</span>
            <a class="month-change-btn" href="#"
              v-on:click.prevent="onClickNextDay(currentYear, currentMonth, day)">▶</a>
          </div>
          <!--카로셀-->
          <div>
            <!-- <img :src="recordPicture" alt=""> -->
          </div>

          <div class="row">
            <img :src="imageUrl" alt="">
            {{dayExerciseList}}
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
      imageUrl: 'data:image/png;base64,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BUM764mItA2tAIjslerYLBt9Tox2nhl/R/u9jVD2QCsA0m5UAET2WbWjUhk9NUbOBjsfXSYoBRUAaTcqACLDcksG/31yCPFdwKzUaeT//YyhFxO9YKT+QRUAaTe6B0BkWKbnMO97MfrS1ElK7nGwW818VowcHWPXy9y5P3UokWZWSR1ARGQ/bAL7DvjdMfpd0L0mdSCRVqMCICLNbquZ/dg9rjELq/Oc1TBvnV61KzI8KgAi0ky2A+vB14CtiZE10H8fVAf/8q91Jgkn0k5UAESa02Nmdpl7nAw22Z3jzJgMHEXr/9xG8EfdbYOZrQdfb8b6PI/rYXADVOupA4qUQasfSETaVX+ed35p5y+4A9w4Cp6cVKnkk2MMx4FPdmdSCPZ8d5/I0FsOJ6QIvJPfARvN2OTOb4GN4I+asWHoJL99A1T74enPJCIpqACItJRZ24H19Trrd/5qnu/8p2oFxk6E7RMhTMwye4E7zwebCIwZ+jt+EFgGbmCHALh7BXjen/8d32oWBsC3gfVD7IewDXwAfCswaMYT4P+b57YR8t9CfdOzl+tFpBmpAIi0nWod+O2O/55RDvaNfkMXaV/aB0BERKSEVABERERKSAVARESkhFQARERESkgFQEREpIRUAEREREpIBUBERKSEVABERERKSAVARESkhFQARERESkgFQEREpIRUAEREREpIBUBERKSEVABERERKSAVARESkhFQARERESkgFQEREpIRUAEREREpIBUBERKSEVABERERKSAVARESkhFQARERESkgFQEREpIRUAEREREpIBUBERKSEVABERERKSAVARESkhFQARERESkgFQEREpIRUAEREREpIBUBERKSEVABERERKSAVARESkhFQARERESkgFQEREpIRUAEREREpIBUBERKSEVABERERKSAVARESkhCx1AJHmsmx8CAPT3e0tZv4ysBcCo1On2oOngN8A95n5l/J84KtQracONTLcKpXaGTHaucBU4HDg0MSh9mQ7sNGdn5txW4z1m+FDv0kdSuSZVABEALglC+HhK4Fu4JDUaYbpZyH4FfV69+2pgwxP36uzzK9z529TJxmmbWDLYxz1YbhiW+owIk9TARChdrCZrTTzM1MnGUEOVGPsvBbMU4fZV1lWm+nOx4ADUmcZKWaszvP6W7UaIM1CBUBK7sZRZk9804w3pE7SIPNj7FqUOsS+yLK+i9z9U6lzNMi6GHk1dD2ZOoiIbgKUUgvh971tfPIHuLZS6T0tdYi9t3CKu69InaKBjg+Bdv580kJUAKTElhwD/sHUKRosxMi/QLUlftbNKn1AR+ocDTYdalNThxBpiYOCSCOEkM+m+e/wHwF2YqUyZlrqFHu26Hgzf3PqFEUw4/LUGURUAKTMzkkdoCgxetN/1hCyt6XOUBQzzoTq2NQ5pNxUAKSklk4EjkmdoihmvDJ1hj1x95NSZyjQWBh7fOoQUm4qAFJS9cNSJyiSux2eOsOehEDTZxxJWVauzyvNRwVASipvuWfjh8db4PO23n4FwxNL9nml2agASEnZ46kTFMmM/0mdYU/cmz/jSMrzoA2BJCkVACmpni3AI6lTFMXdV6fOsGdxTeoEBfoTbF2XOoSUmwqAlNmXUwcoSgjN/1lj9KbPOFLc+SZU+1PnkHJTAZDSijFbDgykzlGAtfX6wLdTh9iznnXufD11iiK4x2WpM4ioAEiJzX0EbHnqFA0WQ7DLoRpTB9kb7nQC7f6b8UrouTt1CBEVACm1GA+Z704L/Ha83+bX6513pg6x97oeNGNm6hQNtC5G3p86hAioAEjpzdruzrnufCN1khEWgati7OxLHWRf5XnXZ8x8JjCYOstIMuO+GG2a3gQozUIFQISuJ92PPRt8DvBE6jQj4MEQ/E0xdi1o1Wfr87z7phjtFDPuTZ1lBGwFFuV5/6nQWapHHaW5WeoAIs2l99AQbLo7Z5vxMuAwmv/tdH8AHgPuM+NLeX7s12B6njrUyHCrVGrTYrRzganAEcD4xKH2ZBBso7v/3My+HmP9Zphfqn0npDX8H5K+njwI1aq/AAAAAElFTkSuQmCC',
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
