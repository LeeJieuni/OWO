<template>
  <div>
    <button @onclick="openmodal"
    v-b-modal.after-exercise-modal class="btn btn-outline-secondary m-2">
      <img class="menu_icon2" src="@/assets/icon/room_end.png" alt="mic_off">
      종료
    </button>
    <b-modal id="after-exercise-modal" size="lg" title="#오운완" :hide-footer="true">
      <div class="after-exercise">
        <h1 class="text-center">오늘의 운동 사진 고르기📷</h1>
        <br>
        <div>
          <div class="d-flex justify-content-center">
            <div class="row" style="width:100%">
              <div id="pick_photo_1" class="col-4 mb-4 hi"
                style="background-color:gray; width:30%; height:150px;">여기를 눌러보세요</div>
              <div class="col-4 mb-4" style="background-color:gray; width:30%; height:150px;"></div>
              <div class="col-4 mb-4" style="background-color:gray; width:30%; height:150px;"></div>
              <div class="col-4 mb-4" style="background-color:gray; width:30%; height:150px;"></div>
              <div class="col-4 mb-4" style="background-color:gray; width:30%; height:150px;"></div>
              <div class="col-4 mb-4" style="background-color:gray; width:30%; height:150px;"></div>
            </div>
          </div>
          <div>오늘 운동을 기록할 사진을 골라주세요!</div>
        </div>
        <br>
        <div class="text-center">나만의 태그 등록하기</div>
        <div class="d-flex justify-content-center">
          <button class="btn btn-primary m-2">#유산소</button>
          <button class="btn btn-primary m-2">#헬스</button>
          <button class="btn btn-primary m-2">#스트레칭</button>
          <button class="btn btn-primary m-2">#맨몸운동</button>
          <button class="btn btn-primary m-2">#요가</button>
          <button class="btn btn-primary m-2">#필라테스</button>
          <button class="btn btn-primary m-2">#기타</button>
        </div>
        <div class="text-center">메모 남기기</div>
        <label for="story"><textarea id="story" name="story" rows="5" cols="33">Tell us your story:
        It was a dark and stormy night...
        </textarea></label>
      </div>
    </b-modal>
  </div>
</template>

<script>
export default {
  data() {
    return {
    };
  },
  methods: {
    hello() {
      console.log('hello');
    },
    openmodal() {
      window.onload = function () {
        alert('hello');
      };
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
  width: 25px;
}

.hi:visited {
  border: 5px solid black;
}
</style>
