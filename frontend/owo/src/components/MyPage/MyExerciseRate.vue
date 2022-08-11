<template>
  <div>
    <div class="row">
        <p class="my-rate-title">나의 운동 비율</p>

      </div>
      <div class="my-rate-info align-items-center">
        <div class="bar-info">
          <div class="progress bar">
              <template v-for="(recordName, i) in state.recordNames" :key="i">
                <div v-if="percentage"
                  class="progress-bar"
                  role="progressbar" aria-label="Segment one"
                  :style="{width: state.recordHours[i] + '%',
                    backgroundColor: state.colors[i] }"
                  :aria-valuenow=state.recordHours[i]
                  aria-valuemin="0" aria-valuemax="100" >
                  <span class="rate-name">
                    {{recordName}} <span class="rate-rate">{{state.recordHours[i]}}%</span>
                  </span>
                </div>
                <div v-else
                  class="progress-bar"
                  role="progressbar" aria-label="Segment one"
                  :style="{width: record.exerciseRate + '%',
                    backgroundColor: '#828282' }"
                  :aria-valuenow="100"
                  aria-valuemin="0" aria-valuemax="100">
                </div>
              </template>
          </div>
          <p class="rate-comment">{{ user.nick }}님은 유산소를 많이 하셨군요!</p>
        </div>
      </div>
  </div>
</template>
<script>
import { useStore } from 'vuex';
import { computed, reactive } from 'vue';

export default {
  name: 'MyExerciseRate',
  components: {},
  setup() {
    const store = useStore();
    const user = computed(() => store.getters['accounts/userInfo']);
    const percentage = computed(() => store.getters['record/percentage']);
    const state = reactive({
      colors: [
        '#6842FF', '#3C48E8', '#4E8AFF', '#3CA3E8', '#42E5FF', '#31E8CE', '#36FFAA',
      ],
      recordNames: [],
      recordHours: [],
    });
    const update = function (userInfo) {
      // console.log(userInfo);
      store.dispatch('accounts/updateUserInfo', userInfo);
    };
    return {
      state,
      user,
      percentage,
      update,
      // percentKeys,
    };
  },
  // data() {
  //   return {
  //     sumOfExerciseHours: 0,
  //     records: [
  //       { name: '유산소', exerciseHours: 24, exerciseRate: null },
  //       { name: '헬스', exerciseHours: 20, exerciseRate: null },
  //       { name: '스트레칭', exerciseHours: 15, exerciseRate: null },
  //       { name: '맨몸운동', exerciseHours: 12, exerciseRate: null },
  //       { name: '요가', exerciseHours: 11, exerciseRate: null },
  //       { name: '필라테스', exerciseHours: 3, exerciseRate: null },
  //       { name: '기타', exerciseHours: 2, exerciseRate: null },
  //     ],
  //   };
  // },
  created() {
    console.log('퍼센트 출력');
    const percentKeys = Object.keys(this.percentage);
    // for (let key in percentage) {
    //   const value = percentage[key];
    //   console.log(key);
    //   console.log(value);
    // }
    console.log(percentKeys);
    // console.log(percentKeys.length);
    for (let i = 0; i < percentKeys.length; i += 1) {
      console.log('퍼센트 키 하나씩 출력');
      console.log(percentKeys[i]);
      console.log(this.percentage[percentKeys[i]]);
      this.state.recordNames.push(percentKeys[i]);
      this.state.recordHours.push(this.percentage[percentKeys[i]]);
    //   // console.log(this.percentage[percentKeys[i]]);
    //   // this.state.records.append(this.percentage[percentKeys[i]]);
    //   // this.sumOfExerciseHours += this.records[i].exerciseHours;
    }
    console.log('결과');
    console.log(this.state.recordNames);
    console.log(this.state.recordHours);
    // for (let i = 0; i < this.records.length; i += 1) {
    //   this.records[i].exerciseRate = Math.round(
    //     (this.records[i].exerciseHours / this.sumOfExerciseHours) * 100,
    //   );
    // }
  },
  moundted() {},
  unmounted() {},
  methods: {
  },
  computed() {

  },
};
</script>

<style scoped>
  /* 나의 운동 레벨 */

.my-rate-title {
  text-align: left;
  padding-top: 30px;
  font-size: 18px;
  padding-left: 40px;
  padding-bottom: 10px;
}

.my-rate-info {
  width: 90%;
  display: flex;
  padding-left: 40px;
  height: 60px;

}

.my-rate-icon {
  width: 100px;
}
.my-rate-icon-img {
  width: 60px;
}
.bar-info {
  width: 100%;
  padding-left: 20px;
}

.progress {
  height: 20px;
}
.progress-bar {
  font-size: 12px;
}
.rate-name {
  font-size: 14px;
}
.rate-rate {
  font-size: 12px;
}
.rate-comment {
  text-align : left;
  font-size: 14px;
  margin-top: 10px;
}
</style>
