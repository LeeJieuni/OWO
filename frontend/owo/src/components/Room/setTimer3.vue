<template>
<div style="position:relative">
  <div v-show="isStarted" class="circle2">
    <svg width="600" style="background-color:white; border-radius: 50%;" viewBox="0 0 220 220" xmlns="http://www.w3.org/2000/svg">
      <g transform="translate(110,110)">
        <circle r="150" class="e-c-base2"/>
        <g transform="rotate(-90)">
          <circle r="100" class="e-c-progress2"/>
          <g id="e-pointer2">
            <circle cx="100" cy="0" r="8" class="e-c-pointer2"/>
          </g>
        </g>
      </g>
    </svg>
  </div>
  <div v-show="isStarted" class="controlls2">
    <div class="display-remain-time2" style="font-size:300px;">{{timesetting}}</div>
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
      wholeTime: 5,
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
      const progressBar = document.querySelector('.e-c-progress2');
      progressBar.style.strokeDasharray = this.length;
      if (this.isStarted === false) {
        this.timer(this.wholeTime);
        this.isStarted = true;
      }
    },
    update(value, timePercent) {
      const offset = (+this.length) - (this.length * value) / (timePercent);
      const progressBar = document.querySelector('.e-c-progress2');
      progressBar.style.strokeDashoffset = offset;
      const pointer = document.getElementById('e-pointer2');
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

button[data-setter] {
  outline: none;
  background: transparent;
  border: none;
  font-family: 'Roboto';
  font-weight: 300;
  font-size: 18px;
  width: 25px;
  height: 30px;
  color: #4E8Aff;
  cursor: pointer;
}

button[data-setter]:hover { opacity: 0.5; }

.container {
  position: relative;
  top: 0px;
  width: 0px;
  margin: 0 auto;
}

.setters {
  position: absolute;
  left: 85px;
  top: 75px;
}

.minutes-set {
  float: left;
  margin-right: 28px;
}

.seconds-set { float: right; }

.controlls2 {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.display-remain-time2 {
  font-family: 'Roboto';
  font-weight: 100;
  font-size: 40px;
  color: #4E8Aff;
}

#pause {
  outline: none;
  background: transparent;
  border: none;
  margin-top: 10px;
  width: 50px;
  height: 50px;
  position: relative;
}

.play::before {
  display: block;
  content: "";
  position: absolute;
  top: 8px;
  left: 16px;
  border-top: 15px solid transparent;
  border-bottom: 15px solid transparent;
  border-left: 22px solid #4E8Aff;
}

.pause::after {
  content: "";
  position: absolute;
  top: 8px;
  left: 12px;
  width: 15px;
  height: 30px;
  background-color: transparent;
  border-radius: 1px;
  border: 5px solid #4E8Aff;
  border-top: none;
  border-bottom: none;
}

#pause:hover { opacity: 0.8; }

.e-c-base2 {
  fill: none;
  stroke: black;
  stroke-width: 20px
}

.e-c-progress2 {
  fill: none;
  stroke: #4E8Aff;
  stroke-width: 4px;
  transition: stroke-dashoffset 0.7s;
}

.e-c-pointer2 {
  fill: #FFF;
  stroke: #4E8Aff;
  stroke-width: 2px;
}

#e-pointer2 { transition: transform 0.7s; }
h1 { margin-top:150px; text-align:center;}
body { background-color:#f7f7f7;}

.menu_icon2 {
  width:50px;
}

.circle2 {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index:1000;
}

</style>
