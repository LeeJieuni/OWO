<template>
    <div style="width:700px;">
      <div class="d-flex justify-content-between align-items-center">
        <div class="d-flex align-items-center m-0">
            <h3 v-if="timer" class="m-0">
                {{timerHour}} : {{ timerMinute }} : {{ timerSecond }}
            </h3>
        </div>
        <div>
            <button v-if="!timer" @click="startTimer">시작 하기</button>
            <button v-if="timerONOFF & (timer>1)" @click="startTimer">다시 시작</button>
            <button v-if="!timerONOFF" @click="stopTimer">휴식 하기</button>
        </div>
      </div>
    </div>
</template>

<script>
export default {
  components: {
  },
  data() {
    return {
      timer: null,
      timerHour: '00',
      timerMinute: '00',
      timerSecond: '00',
      timerONOFF: true,
    };
  },
  setup() {},
  created() {},
  moundted() {
    setTimeout(() => {
      this.startTimer();
    });
  },
  unmounted() {},
  methods: {
    startTimer() {
      this.timerONOFF = false;
      this.timer = setInterval(() => {
        this.timerSecond = parseInt(this.timerSecond, 10) + 1;
        if (parseInt(this.timerSecond, 10) < 10) {
          this.timerSecond = 0 + String(this.timerSecond);
        } else if (parseInt(this.timerSecond, 10) >= 60) {
          this.timerSecond = '00';
          this.timerMinute = parseInt(this.timerMinute, 10) + 1;
          if (parseInt(this.timerMinute, 10) < 10) {
            this.timerMinute = 0 + String(this.timerMinute);
          } else if (parseInt(this.timerMinute, 10) >= 60) {
            this.timerMinute = '00';
            this.timerHour = parseInt(this.timerHour, 10) + 1;
            if (parseInt(this.timerHour, 10) < 10) {
              this.timerHour = 0 + String(this.timerHour);
            } else if (parseInt(this.timerHour, 10) >= 60) {
              this.timerHour = '00';
            }
          }
        }
      }, 1000);
    },
    stopTimer() {
      this.timerONOFF = true;
      clearInterval(this.timer);
    },
  },
};

</script>
<style scoped>
p {
    font-size:5em;
    color:black;
}
button {
    border: none;
    padding: 18px 10px;
    font-size: 1.2em;
    width:180px;
    margin: 10px;
    background-color: gray;
}
button:hover{
    cursor:pointer;
    background-color: #4e8aff;
    color:#fff;
    transition: 0.4s;
    border: none;
}
</style>
