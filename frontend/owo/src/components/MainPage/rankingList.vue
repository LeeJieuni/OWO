<template>
<div>
  <h1>오늘의 운동왕은 누구?</h1>
  <b-card-group deck>
    <b-card title="7월 둘째주 운동왕"  class="rounded-5">
      <div class="ranking1">
        <b-button block pill size='lg' variant="primary">
          {{userrank.rate}}위 {{userrank.username}}님  {{userrank.workoutTime}}분</b-button>
      </div>
      <div class="ranking2">
        <b-button block pill size='lg' variant="outline-secondary">
          {{userrank.rate}}위 {{userrank.username}}님 {{userrank.workoutTime}}분</b-button>
      </div>
      <div class="ranking3">
        <b-button block pill size='lg' variant="outline-danger">
          {{userrank.rate}}위 {{userrank.username}}님 {{userrank.workoutTime}}분</b-button>
      </div>
      <hr/>
      <div class="myranking">
        <b-button block pill size='lg' variant="outline-danger">
          {{userrank.rate}}위 {{userrank.username}}님 {{userrank.workoutTime}}분</b-button>
      </div>
    </b-card>

    <b-card title='"운동왕의 Record"' class="rounded-5">
      <h2>{{userrank.username}}</h2>
      <div class="d-flex justify-content-center" sytle="margin-bottom: 10px">
        <circle-progress class="progress-bar" :percent="30" :show-percent="true"
        :viewport="true" :size="220"/>
      </div>
      <h3 style="margin-top:10px">연속 3일동안 운동하고 있어요!</h3>
    </b-card>

    <b-card title="나의 운동분석" class="rounded-5">
      <!-- <Calendar/> -->
      <physicalInfo/>
    </b-card>
  </b-card-group>
</div>
</template>

<script>
import 'vue3-circle-progress/dist/circle-progress.css';
import CircleProgress from 'vue3-circle-progress';
import PhysicalInfo from '../MyPage/PhysicalInfo.vue';
// import Calendar from '@/components/MyPage/MyCalendar.vue';

export default {
  components: {
    CircleProgress,
    PhysicalInfo,
    // Calendar,
  },
  data() {
    return {
      value: 'test',
      userrank: {
        rate: 1,
        username: '백한나',
        workoutTime: 10,
      },
    };
  },
};
</script>

<style>
.ranking1 {
  padding: 10px;
}
.ranking2 {
  padding: 10px;
}
.ranking3 {
  padding: 10px;
}
.myranking {
  padding: 10px;
}
.progress-bar {
  font-size: 40px;
  font-weight: 200;
}
</style>
