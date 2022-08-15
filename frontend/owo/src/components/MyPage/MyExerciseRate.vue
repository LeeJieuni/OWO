<template>
  <div>
    <div class="row mb-3">
      <div class="col my-rate-title">
        <p class=" p-0 m-0">나의 운동 비율</p>
      </div>
      <div class="col refresh">
        <!--eslint-disable-next-line-->
        <svg @click="refresh()" class="refresh-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="none" d="M0 0h24v24H0z"/><path d="M5.463 4.433A9.961 9.961 0 0 1 12 2c5.523 0 10 4.477 10 10 0 2.136-.67 4.116-1.81 5.74L17 12h3A8 8 0 0 0 6.46 6.228l-.997-1.795zm13.074 15.134A9.961 9.961 0 0 1 12 22C6.477 22 2 17.523 2 12c0-2.136.67-4.116 1.81-5.74L7 12H4a8 8 0 0 0 13.54 5.772l.997 1.795z" fill="rgba(130,130,130,1)"/></svg>
      </div>
    </div>
    <div class="my-rate-info align-items-center">
      <div class="bar-info">
        <div class="progress bar">
            <template v-for="(recordName, i) in percentage.recordNames" :key="i">
              <div v-if="percentage"
                class="progress-bar"
                role="progressbar" aria-label="Segment one"
                :style="{width: percentage.recordHours[i] + '%',
                  backgroundColor: state.colors[i] }"
                :aria-valuenow=percentage.recordHours[i]
                aria-valuemin="0" aria-valuemax="100" >
                <span class="rate-name">
                  {{recordName}} <span
                    class="rate-rate">{{percentage.recordHours[i]}}%</span>
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
        <p v-if="!percentage.recordNames.length == 0"
          class="rate-comment">{{ user.nick }}님은 {{ percentage.best }}를 많이 하셨군요!</p>
        <router-link v-else class="no-record" to="/">운동을 시작해 볼까요?</router-link>
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
    const percentage = computed(() => store.getters['accounts/percentage']);
    console.log('percentage');
    console.log(percentage);
    const state = reactive({
      colors: [
        '#6842FF', '#3C48E8', '#4E8AFF', '#3CA3E8', '#42E5FF', '#31E8CE', '#36FFAA',
      ],
      recordNames: [],
      recordHours: [],
      best: '',
    });
    const update = function (userInfo) {
      // console.log(userInfo);
      store.dispatch('accounts/updateUserInfo', userInfo);
    };
    const refresh = function () {
      store.dispatch('accounts/fetchPercentage');
    };
    console.log('퍼센트');
    console.log(percentage.value);
    return {
      state,
      user,
      percentage,
      refresh,
      update,
      // percentKeys,
    };
  },
  created() {
    // // console.log('퍼센트 출력');
    // const percentKeys = Object.keys(this.percentage);
    // // }
    // // console.log(percentKeys);
    // // console.log(percentKeys.length);
    // for (let i = 0; i < percentKeys.length; i += 1) {
    //   this.state.recordNames.push(percentKeys[i]);
    //   this.state.recordHours.push(this.percentage[percentKeys[i]]);
    // }
    // const bestExercise = percentKeys[0];
    // this.state.best = bestExercise;
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
.no-record {
text-decoration: none;
font-weight: 900;
/* font-size: 20px; */
}
.no-record:hover {
  color: orange;
}

.my-rate-title {
  text-align: left;
  padding-top: 30px;
  font-size: 18px;
  padding-left: 40px;
  padding-bottom: 10px;
}
.refresh {
  margin-top: 30px;
  margin-right: 80px;
  padding: 0;
  /* padding-left: 40px; */
  /* padding-bottom: 10px; */
  font-size: 18px;
  text-align: end;
  /* border: solid black 1px; */
}
.refresh-icon:hover {
  cursor: pointer;
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
