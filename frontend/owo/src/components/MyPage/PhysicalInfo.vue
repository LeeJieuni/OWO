<template>
  <div class="col">
    <div class="row">
      <p class="my-physical-info-title text-center">신체 정보 분석</p>
    </div>

    <!--체질량지수(bmi)-->
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
                + opacityActive.underWeight + ';'"
              aria-valuenow="25"
              aria-valuemin="0" aria-valuemax="100" >저체중
            </div>
            <div class="progress-bar"
              role="progressbar" aria-label="Segment 2"
              :style="'width: 20%; background-color: #007c00; opacity:'
                + opacityActive.normalWeight + ';'"
              aria-valuenow="20"
              aria-valuemin="0" aria-valuemax="100">정상
            </div>
            <div class="progress-bar"
              role="progressbar" aria-label="Segment 3"
              :style="'width: 15%; background-color: #995100;opacity:'
                + opacityActive.overWeight + ';'"
              aria-valuenow="15"
              aria-valuemin="0" aria-valuemax="100">과체중
            </div>
            <div class="progress-bar"
              role="progressbar" aria-label="Segment 4"
              :style="'width: 40%; background-color: #820000; opacity:'
                + opacityActive.obesity + ';'"
              aria-valuenow="40"
              aria-valuemin="0" aria-valuemax="100">비만
            </div>
          </div>
          <div v-if="bmi > 0" class="yes-bmi">
          <div class="bmi-solution pt-2 text-start">
            bmi 지수 : {{bmi}}
            <p class="bmiText" v-if="bmi < 18.5">
              저체중입니다. 헬스로 근육을 찌워보시는 건 어떨까요?
            </p>
            <p class="bmiText" v-if="bmi >= 18.5 && bmi < 23">
              Good! {{ user.name }}님은 정상 체중이네요!
            </p>
            <p class="bmiText" v-if="bmi >= 23 && bmi < 25">
              과체중 솰라솔라
            </p>
            <p class="bmiText" v-if="bmi >= 25">
              비만 어쩌구
            </p>
          </div>
        </div>
        <div v-else class="no-bmi">
          <div class="bmi-solution pt-2 text-start">
            <p class="rg-font">
              {{bmi}}
              정보가 부족합니다.
              <router-link to="/mypage/update" class="link">{{bmiNotEnoughInfo}} 추가하기</router-link>
            </p>
          </div>
        </div>
        </div>
      </div>
    </div>

    <!--칼로리1 기초대사량-->
    <div class="row analy-div bmr">
      <div class="sm-div">
        <p class="md-title"><span id="bmrDescription">기초대사량</span></p>
        <b-popover target="bmrDescription" triggers="hover" placement="bottom">
          <template #title>BMR</template>
          숨만 쉬고 잠만 잘 때 사용되는 생명 유지를 위한 <b>최소한의 열량</b>
        </b-popover>
        <div class="info">
          <div class="progress">
            <div class="progress-bar"
              role="progressbar" aria-label="Segment 1"
              :style="'width:' + bmrPercent +'%; background-color:' + bmrColor"
              :aria-valuenow="bmr"
              :aria-valuemin="avgMinBmr" :aria-valuemax="avgMaxBmr">
            </div>
          </div>
          <div class="mmr-bar-info d-flex">
            <div class="me-auto">{{avgMinBmr}}</div>
            <div class="">{{avgMaxBmr}}</div>
          </div>
          <p class="kcal" :style="'color:' + bmrColor">{{ bmr }} kcal</p>
          <p>{{ bmrText }}</p>
        </div>
      </div>
    </div>

    <!--칼로리2 하루섭취-->
    <div class="row analy-div bmr mb-3">
      <div class="sm-div">
        <p class="md-title">하루 섭취 권장 칼로리</p>
        <div class="info">
          <p class="kcal">{{ caloriePerDay }} kcal</p>
          <a href="https://www.fatsecret.kr/%EC%B9%BC%EB%A1%9C%EB%A6%AC-%EC%98%81%EC%96%91%EC%86%8C/">음식 칼로리 계산하러 가기</a>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { createNamespacedHelpers } from 'vuex';

const { mapGetters } = createNamespacedHelpers('accounts');

export default {
  name: 'PhysicalInfo',
  components: {},
  data() {
    return {
      user: {
        name: '',
        bmi: '',
        gender: '',
        weight: '',
        height: '',
        age: '',
        activityNum: '',
      },
      opacityActive: {
        underWeight: '50%',
        normalWeight: '50%',
        overWeight: '50%',
        obesity: '50%',
      },
      bmi: 0,
      bmr: 0,
      caloriePerDay: 0,
      bmiNotEnoughInfo: '',
      avgMinBmr: 0,
      avgMaxBmr: 0,
      bmrText: '',
      bmrPercent: 0,
      bmrColor: '',
    };
  },
  setup() {},
  created() {
    this.bmi = this.physicalInfo.bmi;
    this.bmr = this.physicalInfo.bmr;
    this.user.age = this.userInfo.age;
    this.user.gender = this.userInfo.gender;
    this.user.height = this.userInfo.height;
    this.user.weight = this.userInfo.weight;
    this.user.activity = this.userInfo.activityLevel;
    this.user.id = this.userInfo.id;
    this.user.nick = this.userInfo.nick;
    if (this.userInfo.gender === '' || this.userInfo.weight === '' || this.userInfo.weight === '' || this.userInfo.height === '' || this.userInfo.activityNum === '') {
      this.bmr = '신체정보를 추가해주세요!';
    }
    // 성별에 따른 기초대사량 계산 (미플린-지어(Mifflin-St.Jeor)공식)
    if (this.userInfo.gender === 'femail') {
      this.bmr = Math.round(
        (10 * this.userInfo.weight) + (6.25 * this.userInfo.height) - (5 * this.userInfo.age) - 161,
      );
    } else {
      this.bmr = Math.round(
        (10 * this.userInfo.weight) + (6.25 * this.userInfo.height) - (5 * this.userInfo.age) + 5,
      );
    }
    const activityrule = [1.2, 1.375, 1.55, 1.725, 1.9];

    // BMI
    this.caloriePerDay = Math.round(activityrule[this.userInfo.activityNum - 1] * this.bmr);
    // console.log(this.userInfo);
    this.bmi = Math.round(this.userInfo.weight / ((this.userInfo.height / 100) ** 2));
    if (this.bmi === 0) {
      if (this.userInfo.weight === 0 && this.userInfo.height > 0) {
        this.bmiNotEnoughInfo = '몸무게';
      } else if (this.userInfo.weight > 0 && this.userInfo.height === 0) {
        this.bmiNotEnoughInfo = '키';
      } else if (this.userInfo.weight === 0 && this.userInfo.height === 0) {
        this.bmiNotEnoughInfo = '몸무게, 키';
      }
    }

    // 성별, 나이별 평균 기초대사량
    if (this.userInfo.gender === 'male') {
      if (this.userInfo.age >= 50) {
        this.avgMinBmr = Math.round(1498.3 - 228.6);
        this.avgMaxBmr = Math.round(1498.3 + 315.3);
      } else if (this.userInfo.age >= 30 && this.userInfo.age < 50) {
        this.avgMinBmr = Math.round(1669.5 - 302.1);
        this.avgMaxBmr = Math.round(1669.5 + 302.1);
      } else if (this.userInfo.age >= 20 ** this.userInfo.age < 30) {
        this.avgMinBmr = Math.round(1728 - 368.2);
        this.avgMaxBmr = Math.round(1728 + 368.2);
      }
    } else if (this.userInfo.gender === 'female') {
      if (this.userInfo.age >= 50) {
        this.avgMinBmr = Math.round(1252.5 - 228.6);
        this.avgMaxBmr = Math.round(1252.5 + 228.6);
      } else if (this.userInfo.age >= 30 && this.userInfo.age < 50) {
        this.avgMinBmr = Math.round(1316.8 - 225.9);
        this.avgMaxBmr = Math.round(1316.8 + 225.9);
      } else if (this.userInfo.age >= 20 ** this.userInfo.age < 30) {
        this.avgMinBmr = Math.round(1311.5 - 233);
        this.avgMaxBmr = Math.round(1311.5 + 233);
      }
    }
    // 기초대사량이 평균에 속하면
    if (this.bmr >= this.avgMinBmr && this.bmr <= this.avgMaxBmr) {
      this.bmrColor = '#198754';
      this.bmrText = '기초대사량이 평균에 속합니다.';
      this.bmrPercent = Math.round(
        ((this.bmr - this.avgMinBmr) / (this.avgMaxBmr - this.avgMinBmr)) * 100,
      );
    } else if (this.bmr > this.avgMaxBmr) {
      this.bmrColor = '#820000';
      this.bmrText = '기초대사량이 평균보다 높네요!';
      this.bmrPercent = 100;
    } else if (this.bmr < this.avgMinBmr) {
      this.bmrColor = '#d7c100';
      this.bmrText = '기초대사량이 평균보다 낮군요.';
      this.bmrPercent = 0;
    }
    console.log(this.bmr);
    // bmi 진단 -> 일단 그래프 색깔 활성화만
    if (this.bmi < 18.5) {
      this.opacityActive.underWeight = '';
    } else if (this.bmi >= 18.5 && this.bmi < 23) {
      this.opacityActive.normalWeight = '';
    } else if (this.bmi >= 23 && this.bmi < 25) {
      this.opacityActive.overWeight = '';
    } else {
      this.opacityActive.obesity = '';
    }
  },
  moundted() {},
  unmounted() {},
  methods: {},
  computed: {
    ...mapGetters(['userInfo', 'physicalInfo']),
  },
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
