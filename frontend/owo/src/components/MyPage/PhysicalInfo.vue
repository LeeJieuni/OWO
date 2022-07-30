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
              style="width: 25%; background-color: #00327c;"
              aria-valuenow="25"
              aria-valuemin="0" aria-valuemax="100" >저체중
            </div>
            <div class="progress-bar"
              role="progressbar" aria-label="Segment 2"
              style="width: 20%; background-color: #007c00;"
              aria-valuenow="20"
              aria-valuemin="0" aria-valuemax="100">정상
            </div>
            <div class="progress-bar"
              role="progressbar" aria-label="Segment 3"
              style="width: 15%; background-color: #995100;"
              aria-valuenow="15"
              aria-valuemin="0" aria-valuemax="100">과체중
            </div>
            <div class="progress-bar"
              role="progressbar" aria-label="Segment 4"
              style="width: 40%; background-color: #820000;"
              aria-valuenow="40"
              aria-valuemin="0" aria-valuemax="100">비만
            </div>
          </div>
          <div v-if="user.bmi > 0" class="yes-bmi">
          <div class="bmi-solution pt-2 text-start">
            bmi 지수 : {{user.bmi}}
            <p class="bmiText" v-if="user.bmi < 18.5">
              저체중입니다. 헬스로 근육을 찌워보시는 건 어떨까요?
            </p>
            <p class="bmiText" v-if="user.bmi >= 18.5 && user.bmi < 23">
              Good! {{ user.name }}님은 정상 체중이네요!
            </p>
            <p class="bmiText" v-if="user.bmi >= 23 && user.bmi < 25">
              과체중 솰라솔라
            </p>
            <p class="bmiText" v-if="user.bmi >= 25">
              비만 어쩌구
            </p>
          </div>
        </div>
        <div v-else class="no-bmi">
          <div class="bmi-solution pt-2 text-start">
            <p class="rg-font">
              {{user.bmi}}
              정보가 부족합니다.
              <router-link to="/mypage/update" class="link">{{bmiNotEnoughInfo}} 추가하기</router-link>
            </p>
          </div>
        </div>
        </div>
      </div>
    </div>

    <!--칼로리1 기초대사량-->
    <div class="row analy-div meta">
      <div class="sm-div">
        <p class="md-title"><span id="metaDescription">기초대사량</span></p>
        <b-popover target="metaDescription" triggers="hover" placement="bottom">
          <template #title>BMR</template>
          숨만 쉬고 잠만 잘 때 사용되는 생명 유지를 위한 <b>최소한의 열량</b>
        </b-popover>
        <div class="info">
          <div class="progress">
            <div class="progress-bar"
              role="progressbar" aria-label="Segment 1"
              :style="'width:' + metaPercent +'%; background-color:' + metaColor"
              :aria-valuenow="meta"
              :aria-valuemin="avgMinMeta" :aria-valuemax="avgMaxMeta">
            </div>
          </div>
          <div class="meta-bar-info d-flex">
            <div class="me-auto">{{avgMinMeta}}</div>
            <div class="">{{avgMaxMeta}}</div>
          </div>
          <p class="kcal" :style="'color:' + metaColor">{{ meta }} kcal</p>
          <p>{{ metaText }}</p>
        </div>
      </div>
    </div>

    <!--칼로리2 하루섭취-->
    <div class="row analy-div meta mb-3">
      <div class="sm-div">
        <p class="md-title">하루 섭취 권장 칼로리</p>
        <div class="info">
          <p class="kcal">{{ eat }} kcal</p>
          <a href="https://www.fatsecret.kr/%EC%B9%BC%EB%A1%9C%EB%A6%AC-%EC%98%81%EC%96%91%EC%86%8C/">음식 칼로리 계산하러 가기</a>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapState } from 'vuex';

export default {
  name: 'PhysicalInfo',
  components: {},
  data() {
    return {
      styleBarPercent: '"width: 30%"',
      user: {
        name: '한나',
        bmi: 0,
        gender: 'female',
        weight: 44,
        height: 156,
        age: 27,
        activity: 4,
      },
      meta: 0,
      eat: 0,
      bmiNotEnoughInfo: '',
      avgMinMeta: 0,
      avgMaxMeta: 0,
      metaText: '',
      metaPercent: 0,
      metaColor: '',
    };
  },
  setup() {},
  created() {
    if (this.user.gender === '' || this.user.weight === '' || this.user.weight === '' || this.user.height === '' || this.user.activity === '') {
      this.meta = '신체정보를 추가해주세요!';
    }
    // 성별에 따른 기초대사량 계산 (미플린-지어(Mifflin-St.Jeor)공식)
    if (this.user.gender === 'femail') {
      this.meta = Math.round(
        (10 * this.user.weight) + (6.25 * this.user.height) - (5 * this.user.age) - 161,
      );
    } else {
      this.meta = Math.round(
        (10 * this.user.weight) + (6.25 * this.user.height) - (5 * this.user.age) + 5,
      );
    }
    const activityrule = [1.2, 1.375, 1.55, 1.725, 1.9];

    // BMI
    this.eat = Math.round(activityrule[this.user.activity - 1] * this.meta);
    // console.log(this.user);
    this.user.bmi = Math.round(this.user.weight / ((this.user.height / 100) ** 2));
    if (this.user.bmi === 0) {
      if (this.user.weight === 0 && this.user.height > 0) {
        this.bmiNotEnoughInfo = '몸무게';
      } else if (this.user.weight > 0 && this.user.height === 0) {
        this.bmiNotEnoughInfo = '키';
      } else if (this.user.weight === 0 && this.user.height === 0) {
        this.bmiNotEnoughInfo = '몸무게, 키';
      }
    }

    // 성별, 나이별 평균 기초대사량
    if (this.user.gender === 'male') {
      if (this.user.age >= 50) {
        this.avgMinMeta = Math.round(1498.3 - 228.6);
        this.avgMaxMeta = Math.round(1498.3 + 315.3);
      } else if (this.user.age >= 30 && this.user.age < 50) {
        this.avgMinMeta = Math.round(1669.5 - 302.1);
        this.avgMaxMeta = Math.round(1669.5 + 302.1);
      } else if (this.user.age >= 20 ** this.user.age < 30) {
        this.avgMinMeta = Math.round(1728 - 368.2);
        this.avgMaxMeta = Math.round(1728 + 368.2);
      }
    } else if (this.user.gender === 'female') {
      if (this.user.age >= 50) {
        this.avgMinMeta = Math.round(1252.5 - 228.6);
        this.avgMaxMeta = Math.round(1252.5 + 228.6);
      } else if (this.user.age >= 30 && this.user.age < 50) {
        this.avgMinMeta = Math.round(1316.8 - 225.9);
        this.avgMaxMeta = Math.round(1316.8 + 225.9);
      } else if (this.user.age >= 20 ** this.user.age < 30) {
        this.avgMinMeta = Math.round(1311.5 - 233);
        this.avgMaxMeta = Math.round(1311.5 + 233);
      }
    }
    // 기초대사량이 평균에 속하면
    if (this.meta >= this.avgMinMeta && this.meta <= this.avgMaxMeta) {
      this.metaColor = '#198754';
      this.metaText = '기초대사량이 평균에 속합니다.';
      this.metaPercent = Math.round(
        ((this.meta - this.avgMinMeta) / (this.avgMaxMeta - this.avgMinMeta)) * 100,
      );
    } else if (this.meta > this.avgMaxMeta) {
      this.metaColor = '#820000';
      this.metaText = '기초대사량이 평균보다 높네요!';
      this.metaPercent = 100;
    } else if (this.meta < this.avgMinMeta) {
      this.metaColor = '#d7c100';
      this.metaText = '기초대사량이 평균보다 낮군요.';
      this.metaPercent = 0;
    }
  },
  moundted() {},
  unmounted() {},
  methods: {},
  computed: {
    ...mapState(['userInfo']),
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
