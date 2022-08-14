<template>
<div style="position:relative">
  <div v-show="isStarted" class="circle">
    <svg width="700" style="background-color:translate; border-radius: 50%;" viewBox="0 0 220 220" xmlns="http://www.w3.org/2000/svg">
      <g transform="translate(110,110)">
        <circle r="100" class="e-c-base"/>
        <g transform="rotate(-90)">
          <circle r="100" class="e-c-progress"/>
          <g id="e-pointer">
            <circle cx="100" cy="0" r="8" class="e-c-pointer"/>
          </g>
        </g>
      </g>
    </svg>
  </div>
  <div v-show="isStarted" class="controlls">
    <div class="display-remain-time" style="font-size:300px;">{{timesetting}}</div>
  </div>
</div>
</template>

<script>
export default {
  components: {
  },
  data() {
    return {
      intervalTimer: null,
      timeLeft: null,
      wholeTime: 3,
      isPaused: false,
      isStarted: false,
      length: Math.PI * 2 * 100,
      timesetting: '',
    };
  },
  setup() {},
  created() {},
  moundted() {},
  unmounted() {},
  methods: {
    pauseTimer() {
      console.log('시계2작동중');
      const progressBar = document.querySelector('.e-c-progress');
      progressBar.style.strokeDasharray = this.length;
      if (this.isStarted === false) {
        this.timer(this.wholeTime);
        this.isStarted = true;
      }
    },
    update(value, timePercent) {
      const offset = (+this.length) - (this.length * value) / (timePercent);
      const progressBar = document.querySelector('.e-c-progress');
      progressBar.style.strokeDashoffset = offset;
      const pointer = document.getElementById('e-pointer');
      pointer.style.transform = `rotate(${(360 * value) / (timePercent)}deg)`;
    },
    displayTimeLeft(timeLeft) { // displays time on the input
      const seconds = timeLeft % 60;
      const displayString = `${seconds}`;
      this.timesetting = displayString === '0' ? 'GO!' : displayString;
      this.update(timeLeft, this.wholeTime);
    },
    timer(seconds) { // counts time, takes seconds
      const remainTime = Date.now() + (seconds * 1000);
      this.displayTimeLeft(seconds);
      this.intervalTimer = setInterval(() => {
        this.timeLeft = Math.round((remainTime - Date.now()) / 1000);
        console.log(this.timeLeft);
        if (this.timeLeft < 0) {
          clearInterval(this.intervalTimer);
          this.isStarted = false;
          this.displayTimeLeft(this.wholeTime);
          return;
        }
        this.displayTimeLeft(this.timeLeft);
      }, 1000);
    },
  },
};
// update(wholeTime, wholeTime); // refreshes progress bar
// displayTimeLeft(wholeTime);
</script>
<style scoped>
@import url('https://fonts.googleapis.com/css?family=Roboto:100,300');

.controlls {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index:1500;
}

.display-remain-time {
  font-family: 'Roboto';
  font-weight: 100;
  font-size: 40px;
  color: white;
}

.e-c-base {
  fill: none;
  stroke: white;
  stroke-width: 4px
}

.e-c-progress {
  fill: none;
  stroke: #4E8Aff;
  stroke-width: 4px;
  transition: stroke-dashoffset 0.7s;
}

.e-c-pointer {
  fill: #FFF;
  stroke: #4E8Aff;
  stroke-width: 2px;
}

#e-pointer { transition: transform 0.7s; }
h1 { margin-top:150px; text-align:center;}
body { background-color:#f7f7f7;}

.circle {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index:1000;
}

</style>
