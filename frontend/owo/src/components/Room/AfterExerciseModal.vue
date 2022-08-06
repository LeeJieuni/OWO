<template>
  <div>
    <button
    v-b-modal.after-exercise-modal class="btn btn-outline-secondary m-2">
      <img class="menu_icon2" src="@/assets/icon/room_end.png" alt="mic_off">
      운동 종료
    </button>
    <b-modal id="after-exercise-modal" size="lg" hide-footer hide-header>
      <div>
        <h1 class="title text-center mt-4">운동일지 작성하기</h1>
        <div class="wrapper">
          <div class="md-title text-center">오늘의 운동 사진 고르기📷</div>
          <div class="container">
            <div class="row row-cols-3">
              <div v-for="(photo, i) in this.pictures" :key="i" class="col photo">
                <div class="col p-1">
                  <div class="card">
                    <img :src="photo.src" alt="" class=" img-fluid">
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="wrapper">
          <div class="md-title text-center">운동 종류 기록하기</div> <!-- 나만의 태그 기록 -->
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
        </div>
        <div class="wrapper">
          <div class="md-title text-center">메모 남기기</div>
          <div class="d-flex justify-content-center">
            <form action="" id="exerciseMemoForm">
              <label for="exerciseMemo">
                <textarea
                  id="exerciseMemo"
                  name="exerciseJournal"
                  rows="5"
                  cols="90">
                </textarea>
              </label>
            </form>
          </div>
        </div>
        <div class="d-flex justify-content-center">
          <button on @click="exerciseJournalSubmit($event)"
            class="btn btn-primary" form="exerciseMemoForm">작성</button>
        </div>
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
      pictures: [
        { src: 'https://cdn.pixabay.com/photo/2017/08/07/14/02/man-2604149_960_720.jpg' },
        { src: 'https://cdn.pixabay.com/photo/2017/08/07/14/02/man-2604149_960_720.jpg' },
        { src: 'https://cdn.pixabay.com/photo/2017/08/07/14/02/man-2604149_960_720.jpg' },
        { src: 'https://cdn.pixabay.com/photo/2017/08/07/14/02/man-2604149_960_720.jpg' },
        { src: 'https://cdn.pixabay.com/photo/2017/08/07/14/02/man-2604149_960_720.jpg' },
        { src: 'https://cdn.pixabay.com/photo/2017/08/07/14/02/man-2604149_960_720.jpg' },
      ],
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
#after-exercise-modal {
  font-family: 'NanumSquareRound', 'Noto Sans KR', sans-serif;
  /* width: 900px; */
}
.title {
  font-weight: 900;
}
.md-title {
  font-size: 24px;
  font-weight: 800;
  margin: 20px 0;
}
.container {
  height: 400px;
}
.wrapper {
  padding: 20px 0;
}
.photo-row {
  /* width:100%; */
}
.photo {
  /* border: solid 1px black; */
  /* padding: 0 10px; */
  /* margin: 10px; */
  /* background-color:gray; */
  /* width:10%; */
  /* width: 100px;
  height:100px; */
}

.exerciseMemo {
  font-size: 20px;
  font-weight: 700;
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
