<template>
    <!-- eslint-disable-next-line -->
    <div v-if="timer" class="mybtn8">&ensp;{{timerHour}} : {{ timerMinute }} : {{ timerSecond }}&ensp;</div>
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

.mybtn8 {
  color:#4e8aff;
  position:fixed;
  top: 5px;
  right: 210px;
  background-image: url('../../assets/icon/startTimer.png');
  border-radius: 10px;
  background-position: center;
  background-size: 220px;
  background-repeat: no-repeat;
  line-height:1;
  font-size:30px;
  width:220px;
  height:130px;
  display:flex;
  text-align: center;
  align-items: center;
  justify-content: center;
}
</style>
