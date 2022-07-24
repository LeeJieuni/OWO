<template>
  <div>
    <b-button v-b-modal.modal-prevent-closing>Open Modal</b-button>
    <b-modal
      id="modal-prevent-closing"
      ref="modal"
      title="로그인"
      @show="resetModal"
      @hidden="resetModal"
      @ok="handleOk"
      :hide-footer="true"
    >
      <form ref="form" @submit.stop.prevent="handleSubmit">
        <b-form-group
          label="ID"
          label-for="ID-input"
          invalid-feedback="ID is required"
          :state="idState"
        >
          <b-form-input
            id="ID-input"
            v-model="id"
            :state="idState"
            required
          ></b-form-input>
        </b-form-group>
        <b-form-group
          label="password"
          label-for="password-input"
          invalid-feedback="password is required"
          :state="passwordState"
        >
          <b-form-input
            id="password-input"
            v-model="password"
            :state="passwordState"
            required
          ></b-form-input>
        </b-form-group>
      </form>
      <br>
      <div class="signup"><p>회원이 아니신가요? <a href="">회원가입</a>하기</p></div>
      <div class="buttons"><b-button class="loginbtn">로그인</b-button></div>
      <br>
      <hr>
      <div class="snsButtons">
        <h2>소셜로그인</h2>
        <p></p>
        <div class="snsBtn d-flex justify-content-center">
          <!-- <img class="kakao" src="@/assets/socialLogo/" alt=""> -->
          <img class="naver" src="@/assets/socialLogo/naverLogi2.png" alt="">
          <!-- <img class="google" src="@/assets/socialLogo/" alt=""> -->
        </div>
      </div>
    </b-modal>
  </div>
</template>

<style>
.snsBtn a {
  padding: 10px;
}
.snsButtons h2 {
  text-align: center;
}
.signup p {
  text-align: center;
}
.loginbtn {
  width: 80%;
  height: 100%;
}
.buttons {
  display: flex;
  justify-content: center;
  align-items: center;
}
.naver {
  width: 50px;
  height: 50px;
}
.kakao {
  width: 100px;
  height: 50px;
}
</style>
