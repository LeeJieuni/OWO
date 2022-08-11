<template>
  <div>
    <button v-b-modal.room-setting-modal class="btn btn-outline-secondary m-2">
      <img class="menu_icon" src="@/assets/icon/room_setting.png" alt="mic_off">설정
    </button>
    <b-modal id="room-setting-modal" title="#오운완" size="lg" hide-footer>
      <div class="after-exercise">
        <div class="d-flex justify-content-start align-items-center">
          <img class="menu_icon2" src="@/assets/icon/room_setting.png" alt="mic_off">
          <div class="d-flex justify-content-center align-items-center">
            <h1>운동설정&ensp;&ensp;&ensp;</h1>
            <div v-if="lockroomcheck" class="d-flex">
              <img class="menu_icon" src="@/assets/icon/unlock.png" alt="lock">
              <div class="text-center">&ensp;비밀 방&ensp;</div>
              <label class="form-check-label" for="flexCheckDefault">
              <input class="form-check-input" type="checkbox" value=""
              @click="lockroom" id="flexCheckDefault"></label>
              &ensp;&ensp;&ensp;&ensp;비밀번호&ensp;
              <label for="form-password-label"><input class="form-password-input"
              type="password" maxlength="4" size="10" disabled></label>
            </div>
            <div v-if="!lockroomcheck" class="d-flex">
              <img class="menu_icon" src="@/assets/icon/lock.png" alt="lock">
              <div class="text-center">&ensp;비밀 방&ensp;</div>
              <label class="form-check-label" for="flexCheckDefault">
              <input class="form-check-input" type="checkbox" value=""
              @click="lockroom" id="flexCheckDefault"></label>
              &ensp;&ensp;&ensp;&ensp;비밀번호&ensp;
              <label for="form-password-label"><input class="form-password-input"
              type="password" maxlength="4" size="10"></label>
            </div>
          </div>
        </div>
        <br>
        <br>
        <div>방 모드 변경하기</div>
        <br>
        <div class="d-flex justify-content-around">
            <button class="btn btn-primary">친구들과 운동하기</button>
            <button class="btn btn">영상보며 운동하기</button>
        </div>
        <br>
        <br>
        <div class="d-flex justify-content-between">
          <div>
            <div>방 이름 변경</div>
            <br>
            <label for="roomNameChange" class="roomNameChange">
            <input type="text" class="roomNameChange" size="30"></label>
          </div>
          <div>
            <div>운동 변경</div>
            <br>
            <label for="exerciseChange" class="exerciseChange">
            <input type="text" class="exerciseChange" rows="2" size="30"></label>
          </div>
        </div>
        <br>
        <br>
        <div>
          <div>운동 영상 링크</div>
          <br>
          <label for="youtubelink"><textarea id="youtubelink"
          name="youtubelink" size="77" rows="2" cols="77"></textarea></label>
        </div>
        <br>
        <br>
        <div class="d-flex justify-content-center">
          <button class="btn btn-primary">저장</button>&ensp;&ensp;&ensp;&ensp;
          <button class="btn btn">취소</button>
        </div>
      </div>
    </b-modal>
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      lockroomcheck: false,
    };
  },
  setup() {},
  created() {},
  moundted() {},
  unmounted() {},
  methods: {
    lockroom() {
      this.lockroomcheck = !this.lockroomcheck;
    },
  },
};
</script>
<style scoped>
.menu_icon {
    height:30px;
}
.after-exercise {
    width:600px;
    margin:auto;
}
.menu_icon2 {
    height:40px;
    width:40px;
}
</style>
