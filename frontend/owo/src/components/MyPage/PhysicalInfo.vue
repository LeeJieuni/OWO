<template>
  <div class="col">
    <div class="row">
      <p class="my-physical-info-title text-center">신체 정보 분석</p>
    </div>

    <div class="row analy-div bmi">
      <div class="sm-div align-items-center">
        <p class="md-title"><span id="bmiDescription">체질량지수</span></p>
        <b-popover target="bmiDescription" triggers="hover" placement="bottom">
          <template #title>BMI</template>
          비만인지 아닌지 흔히 구분 짓는 요소<br>
          자신의 몸무게(kg)를 키의 제곱(m)으로 나눈 값<br>
          단, 활동량이 많은 경우 비만도가 높아도 비만이 아닐 수 있다.
        </b-popover>
        <div class="info">
          <div class="progress">
            <div class="progress-bar"
              role="progressbar" aria-label="Segment 1"
              :style="'width: 25%; background-color: #00327c; opacity:'
                + state.opacityActive.underWeight + ';'"
              aria-valuenow="25"
              aria-valuemin="0" aria-valuemax="100" >저체중
            </div>
            <div class="progress-bar"
              role="progressbar" aria-label="Segment 2"
              :style="'width: 20%; background-color: #007c00; opacity:'
                + state.opacityActive.normalWeight + ';'"
              aria-valuenow="20"
              aria-valuemin="0" aria-valuemax="100">정상
            </div>
            <div class="progress-bar"
              role="progressbar" aria-label="Segment 3"
              :style="'width: 15%; background-color: #995100;opacity:'
                + state.opacityActive.overWeight + ';'"
              aria-valuenow="15"
              aria-valuemin="0" aria-valuemax="100">과체중
            </div>
            <div class="progress-bar"
              role="progressbar" aria-label="Segment 4"
              :style="'width: 40%; background-color: #820000; opacity:'
                + state.opacityActive.obesity + ';'"
              aria-valuenow="40"
              aria-valuemin="0" aria-valuemax="100">비만
            </div>
          </div>
          <div v-if="state.notEnoughInfo ==='enough'" class="yes-bmi">
          <div class="bmi-solution pt-2 text-start">
            <p>bmi 지수 : {{state.bmi}}</p>
            <p>{{ state.bmiText }}</p>
          </div>
        </div>
        <div v-else class="no-bmi">
          <div class="bmi-solution pt-2 text-start">
            <p class="rg-font">
              {{state.text}}
              <router-link
                to="/mypage/update"
                class="link">{{state.notEnoughInfo}} 추가하기</router-link>
            </p>
          </div>
        </div>
        </div>
      </div>
    </div>

    <div class="row analy-div bmr">
      <div class="sm-div">
        <p class="md-title"><span id="bmrDescription">기초대사량</span></p>
        <b-popover target="bmrDescription" triggers="hover" placement="bottom">
          <template #title>BMR</template>
          숨만 쉬고 잠만 잘 때 사용되는 생명 유지를 위한 <b>최소한의 열량</b>
        </b-popover>
        <div v-if="state.notEnoughInfo ==='enough'" class="info yes-bmr">
          <div class="progress">
            <div class="progress-bar"
              role="progressbar" aria-label="Segment 1"
              :style="'width:' + state.bmrPercent +'%; background-color:' + state.bmrColor"
              :aria-valuenow="physical.bmr"
              :aria-valuemin="state.avgMinBmr" :aria-valuemax="state.avgMaxBmr">
            </div>
          </div>
          <div class="mmr-bar-info d-flex">
            <div class="me-auto">{{state.avgMinBmr}}</div>
            <div class="">{{state.avgMaxBmr}}</div>
          </div>
          <p class="kcal" :style="'color:' + state.bmrColor">{{ state.bmr }} kcal</p>
          <p>{{ state.bmrText }}</p>
        </div>
        <div v-else class="no-bmr">
          정보가 부족합니다. <router-link to="/mypage/update">수정하기</router-link>
        </div>
      </div>
    </div>

    <div class="row analy-div bmr mb-3">
      <div class="sm-div">
        <p class="md-title">하루 섭취 권장 칼로리</p>
        <div class="info" v-if="state.notEnoughInfo ==='enough'">
          <p class="kcal">{{ state.caloriePerDay }} kcal</p>
          <a href="https://www.fatsecret.kr/%EC%B9%BC%EB%A1%9C%EB%A6%AC-%EC%98%81%EC%96%91%EC%86%8C/">음식 칼로리 계산하러 가기</a>
        </div>
        <div v-else>
          <p class="no-bmr">정보가 부족합니다. <router-link to="/mypage/update">수정하기</router-link></p>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { useStore } from 'vuex';
import { reactive, computed } from 'vue';
// import { createNamespacedHelpers } from 'vuex';

// const { mapGetters } = createNamespacedHelpers('accounts');

export default {
  name: 'PhysicalInfo',
  components: {},
  setup() {
    const store = useStore();
    const user = computed(() => store.getters['accounts/userInfo']);
    const physical = computed(() => store.getters['accounts/physicalInfo']);
    const state = reactive({
      opacityActive: {
        underWeight: '50%',
        normalWeight: '50%',
        overWeight: '50%',
        obesity: '50%',
      },
      bmi: 0,
      bmr: 0,
      caloriePerDay: 0,
      bmiText: '',
      notEnoughInfo: 'enough',
      avgMinBmr: 0,
      avgMaxBmr: 0,
      text: '',
      caloriePerDayText: '',
      bmrPercent: 0,
      bmrColor: '',
    });
    return {
      state,
      physical,
      user,
    };
  },
  created() {
    console.log('created');
    console.log(this.state.notEnoughInfo);
    // physical info 반올림
    this.state.bmi = Math.round(this.physical.bmi);
    this.state.bmr = Math.round(this.physical.bmr);
    this.state.caloriePerDay = Math.round(this.physical.caloriePerDay);

    // user 정보 부족 시 bmi, bmr, calorie 초기화
    // eslint-disable-next-line
    if (!this.user.gender || !this.user.weight || !this.user.weight || !this.user.height || !this.user.activityLevel) {
      this.state.text = '신체정보를 추가해주세요!';
      this.state.bmi = '';
      this.state.bmr = '';
      this.state.caloriePerDay = '';
      if (!this.user.weight && this.user.height > 0) {
        this.state.notEnoughInfo = '몸무게';
      } else if (this.user.weight > 0 && !this.user.height) {
        this.state.notEnoughInfo = '키';
      } else if (!this.user.weight && !this.user.height) {
        this.state.notEnoughInfo = '몸무게, 키';
      }
    }

    // 계산 공식들
    // 성별에 따른 기초대사량 계산 (미플린-지어(Mifflin-St.Jeor)공식)
    // if (this.user.gender === 'femail') {
    //   this.physical.bmr = Math.round(
    //     (10 * this.user.weight) + (6.25 * this.user.height) - (5 * this.user.age) - 161,
    //   );
    // } else {
    //   this.physical.bmr = Math.round(
    //     (10 * this.user.weight) + (6.25 * this.user.height) - (5 * this.user.age) + 5,
    //   );
    // }
    // const activityrule = [1.2, 1.375, 1.55, 1.725, 1.9];
    // // BMI
    // this.physical.bmi = Math.round(this.user.weight / ((this.user.height / 100) ** 2));
    // eslint-disable-next-line
    // this.physical.caloriePerDay = Math.round(activityrule[this.user.activityLevel - 1] * this.physical.bmr);

    // // 성별, 나이별 평균 기초대사량
    if (this.user.gender === 'MALE') {
      if (this.user.age >= 50) {
        this.state.avgMinBmr = Math.round(1498.3 - 228.6);
        this.state.avgMaxBmr = Math.round(1498.3 + 315.3);
      } else if (this.user.age >= 30 && this.user.age < 50) {
        this.state.avgMinBmr = Math.round(1669.5 - 302.1);
        this.state.avgMaxBmr = Math.round(1669.5 + 302.1);
      } else if (this.user.age >= 20 ** this.user.age < 30) {
        this.state.avgMinBmr = Math.round(1728 - 368.2);
        this.state.avgMaxBmr = Math.round(1728 + 368.2);
      }
    } else if (this.user.gender === 'FEMALE') {
      if (this.user.age >= 50) {
        this.state.avgMinBmr = Math.round(1252.5 - 228.6);
        this.state.avgMaxBmr = Math.round(1252.5 + 228.6);
      } else if (this.user.age >= 30 && this.user.age < 50) {
        this.state.avgMinBmr = Math.round(1316.8 - 225.9);
        this.state.avgMaxBmr = Math.round(1316.8 + 225.9);
      } else if (this.user.age >= 20 ** this.user.age < 30) {
        this.state.avgMinBmr = Math.round(1311.5 - 233);
        this.state.avgMaxBmr = Math.round(1311.5 + 233);
      }
    }
    // console.log('TEST');
    // console.log(this.user.gender);
    // console.log(this.state.avgMinBmr);
    // // 기초대사량이 평균에 속하면
    if (this.physical.bmr >= this.state.avgMinBmr && this.physical.bmr <= this.state.avgMaxBmr) {
      this.state.bmrColor = '#198754';
      this.state.bmrText = '기초대사량이 평균에 속합니다.';
      this.state.bmrPercent = Math.round(
      // eslint-disable-next-line
        ((this.physical.bmr - this.state.avgMinBmr) / (this.state.avgMaxBmr - this.state.avgMinBmr)) * 100,
      );
    } else if (this.physical.bmr > this.state.avgMaxBmr) {
      this.state.bmrColor = '#820000';
      this.state.bmrText = '기초대사량이 평균보다 높네요!';
      this.state.bmrPercent = 100;
    } else if (this.physical.bmr < this.state.avgMinBmr) {
      this.state.bmrColor = '#d7c100';
      this.state.bmrText = '기초대사량이 평균보다 낮군요.';
      this.state.bmrPercent = 0;
    }
    // console.log(this.bmr);
    // // bmi 진단 -> 일단 그래프 색깔 활성화만
    if (this.physical.bmi && this.physical.bmi < 18.5) {
      this.state.opacityActive.underWeight = '';
      this.state.bmiText = '저체중입니다.';
    } else if (this.physical.bmi >= 18.5 && this.physical.bmi < 23) {
      this.state.opacityActive.normalWeight = '';
      this.state.bmiText = '정상입니다.';
    } else if (this.physical.bmi >= 23 && this.physical.bmi < 25) {
      this.state.opacityActive.overWeight = '';
      this.state.bmiText = '과체중입니다.';
    } else {
      this.state.opacityActive.obesity = '';
      this.state.bmiText = '비만입니다.';
    }
  },
  moundted() {},
  unmounted() {},
  methods: {},
};
</script>

<style scoped>
.sm-div {
  border-bottom: solid #DFDFDF 1px;
  padding-top: 20px;
  padding-bottom: 20px;
}
.my-physical-info-title {
  text-align: left;
  padding-top: 50px;
  font-size: 22px;
  /* padding-bottom: 20px; */
  padding-left: 50px;
}

.analy-div {
  padding: 10px 60px;
  align-items: flex-start;
}

.bar {
  height: 30px;
  border-radius: 10;
}
.md-title {
  font-size: 18px;
  text-align: left;
  margin-bottom: 20px;
}
.info {
  padding-left: 50px;
  padding-right: 50px;
}
.progress-bar {
  font-size: 18px;
}
.progress {
  height: 25px;
  border-radius: 0px;
}
.kcal {
  font-size: 15px;
  color: #00327c;
}
.link {
  /* text-decoration: none; */
  color:#00327c;
  font-size: 12px;
}

.user-percent {
  font-family: 'LeferiPoint-WhiteA';
  font-size: 12px;
}
</style>
