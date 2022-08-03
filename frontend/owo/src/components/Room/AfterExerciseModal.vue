<template>
  <div>
    <button
    v-b-modal.after-exercise-modal class="btn btn-outline-secondary m-2">
      <img class="menu_icon2" src="@/assets/icon/room_end.png" alt="mic_off">
      운동 종료
    </button>
    <b-modal id="after-exercise-modal" title="#오운완" size="lg" hide-footer>
      <div>
        <h1 class="text-center">운동일지 작성하기</h1>
        <br>
        <br>
        <div class="text-center">오늘의 운동 사진 고르기📷</div>
        <br>
        <div>
          <div class="d-flex justify-content-center">
            <div class="row" style="width:100%">
              <div class="col-4 mb-4" style="background-color:gray; width:30%; height:150px;"></div>
              <div class="col-4 mb-4" style="background-color:gray; width:30%; height:150px;"></div>
              <div class="col-4 mb-4" style="background-color:gray; width:30%; height:150px;"></div>
              <div class="col-4 mb-4" style="background-color:gray; width:30%; height:150px;"></div>
              <div class="col-4 mb-4" style="background-color:gray; width:30%; height:150px;"></div>
              <div class="col-4 mb-4" style="background-color:gray; width:30%; height:150px;"></div>
            </div>
          </div>
        </div>
        <br>
        <div class="text-center">나만의 태그 등록하기</div>
        <br>
        <!-- <form action="">
          <label for="my-tag">
          <input v-model="inputMyTag" type="text">
          <button @click="addMyTag($event)">추가</button>
          </label>
        </form>
        <div class="mytags row">
          <div>hi</div>
          <div v-for="(mytag, index) in roomexercise.mytag" :key="index">
            <p>#{{ roomexercise.mytag }}</p>
          </div>
        </div> -->
        <div class="d-flex justify-content-center">
          <button class="btn btn-primary m-2">#유산소</button>
          <button class="btn btn-primary m-2">#헬스</button>
          <button class="btn btn-primary m-2">#스트레칭</button>
          <button class="btn btn-primary m-2">#맨몸운동</button>
          <button class="btn btn-primary m-2">#요가</button>
          <button class="btn btn-primary m-2">#필라테스</button>
          <button class="btn btn-primary m-2">#기타</button>
        </div>
        <br>
        <div class="text-center">메모 남기기</div>
        <br>
        <div class="d-flex justify-content-center">
          <form action="" id="exerciseMemoForm">
            <label for="exerciseMemo"><textarea id="exerciseMemo"
            name="exerciseJournal" rows="5" cols="90"></textarea></label>
            <br>
            <br>
          </form>
        </div>
        <div class="d-flex justify-content-center">
          <button on @click="exerciseJournalSubmit($event)"
          class="btn btn-primary" form="exerciseMemoForm">작성</button>
        </div>
        <br>
      </div>
    </b-modal>
  </div>
</template>

<script>

export default {
  data() {
    return {
      // roomexercise: {
      //   inputMyTag: '',
      //   mytag: [],
      // },
    };
  },
  methods: {
    // addMyTag(event) {
    //   event.preventDefault();
    //   this.roomexercise.myTag.push(this.inputMyTag);
    // },
    exerciseJournalSubmit(event) {
      event.preventDefault();
    },
  },
};
</script>

<style scope>
button {
  padding: 0;
  border: none;
  background: none;
}

.socialbtn {
  margin-top: 20px;
}

.input_email {
  display: flex;
  margin-bottom: 20px;
}

.input_password {
  display: flex;
  margin-bottom: 20px;
}

.alreadyUser {
  text-align: center;
  font-size: 20px;
}

.buttons {
  margin-top: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.menu_icon2 {
  width: 30px;
}
</style>
