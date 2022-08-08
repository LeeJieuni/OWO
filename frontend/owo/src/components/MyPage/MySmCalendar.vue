<template>
  <div class="calendar">
    <div class="month-title">
      <h2>
        <a class="month-change-btn" href="#"
          @click.prevent="onClickPrev(currentMonth)"
        >◀</a><span class="calendar-title">{{currentYear}}년 {{currentMonth}}월</span><a
          class="month-change-btn" href="#" v-on:click.prevent="onClickNext(currentMonth)">▶</a>
      </h2>
    </div>
    <div class="calendar-table">
      <table class="table table-hover">
        <thead>
          <tr>
            <td v-for="(weekName, index) in weekNames" v-bind:key="index">
              {{weekName}}
            </td>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(row, index) in currentCalendarMatrix" :key="index">
            <td class="day-td" v-for="(day, index2) in row" :key="index2">
              <a href="#" v-if="isToday(currentYear, currentMonth, day)"
                class="rounded days"
                @click="selectDay(currentMonth, day)" v-b-modal="'myModal'">
                {{day}}
              </a>
              <span v-else>
                <a class="days" href="#" @click="selectDay(currentMonth, day)"
                  v-b-modal="'myModal'">{{day}}</a>
              </span>
            </td>
          </tr>
        </tbody>
      </table>
      <b-modal id="myModal" size="lg" button-size="sm" scrollable ref="myModal"
      class="myModal"
        :title="`${currentYear}년 ${currentMonth}월 ${day}일`" hide-header>
        <div class="carousel-box">
          <div class="modal-title">
            <p class="lg-title">{{currentYear}}년 {{currentMonth}}월 {{day}}일</p>
          </div>
          <!--카로셀-->
          <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
              <div class="carousel-item active">
                <img src="https://phantom-marca.unidadeditorial.es/746e69f29df0fa7da1f9df1cffc2af10/crop/0x20/1499x861/resize/1320/f/jpg/assets/multimedia/imagenes/2022/01/12/16419960151339.jpg" class="d-block w-100" alt="">
              </div>
              <div class="carousel-item">
                <img src="https://cdn.pixabay.com/photo/2016/11/14/03/38/achieve-1822503__480.jpg" class="d-block w-100" alt="">
              </div>
              <div class="carousel-item">
                <img src="https://cdn.pixabay.com/photo/2018/02/06/14/07/ease-3134828__340.jpg" class="d-block w-100" alt="">
              </div>
            </div>
            <button class="carousel-control-prev" type="button"
              data-bs-target="#carouselExampleControls" data-bs-slide="prev">
              <span class="carousel-control-prev-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button"
              data-bs-target="#carouselExampleControls" data-bs-slide="next">
              <span class="carousel-control-next-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Next</span>
            </button>
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
export default {
  name: 'MySmCalendar',
  data() {
    return {
      weekNames: ['월', '화', '수', '목', '금', '토', '일'],
      rootYear: 1904,
      rootDayOfWeekIndex: 4, // 2000년 1월 1일은 토요일
      currentYear: new Date().getFullYear(),
      currentMonth: new Date().getMonth() + 1,
      currentDay: new Date().getDate(),
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
    };
  },
  mounted() {
    this.init();
  },
  methods: {
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
    isToday(year, month, day) {
      const date = new Date();
      return year === date.getFullYear() && month === date.getMonth() + 1 && day === date.getDate();
    },
    selectDay(currentMonth, day) {
      this.day = day;
    },
    cancle() {
      const modal = document.querySelector('.modal');
      modal('hide');
    },
  },
};
</script>

<style scoped>
.calendar {
  width: 100%;
  margin: 0 auto;
}
.calendar-table {
  /* margin-left: 10px;
  margin-right: 10px; */
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
  padding: 3px;
}
.rounded {
  -moz-border-radius:20px 20px 20px 20px;
  border-radius:20px 20px 20px 20px;
  border:solid 1px #ffffff;
  background-color:#4E8AFF;
  padding: 5px 7px;
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
}
.exercise-type-box {
  margin-bottom: 10px;
}
.modalImg {
  display: flex;
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
  margin: 2px;
  font-size: 12px;
  font-weight: 700;
  background-color:#4E8AFF;
  line-height: 25px;
  color: white;
  letter-spacing: -1.5;
  text-align: center;
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
  text-align: left;
}
table {
  /* --bs-table-hover-bg: rgba(75, 172, 237, 0.08); */
  width: 300px;
  margin: 0 auto;
}
td, th {
  padding: 0;
}
.day-td  {
  padding: 7px;
  --bs-table-hover-bg: rgba(0, 6, 10, 0.08);
  border-bottom: 0;
}
.table > a {
  text-decoration: none;
}
.calendar-title {
  font-size: 18px;
}
</style>
