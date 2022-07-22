<template>
  <div>
    <div class="title">
      <div class="profile">
        <img class="profile-img" src="https://picsum.photos/200" alt="">
      </div>
      <p>{{ user.nick }}님의 정보 수정</p>
      <span>{{ user.slogan }}</span> <span>(연필아이콘)</span>
    </div>
    <form action="">
      <!-- <label for="profile">
        <input name="profile" type="image" alt="" aria-label="profile">
      </label> -->

      <label for="nick"> 닉네임 :
        <input type="text" name="nick">
      </label><br>

      <label for="age"> 나이 :
        <input type="number" name="age">
      </label><br>

      <label for="gender"> 성별 :
        <select name="radio" id="gender">
          <option value="female">여자</option>
          <option value="male">남자</option>
        </select>
      </label><br>

      <label for="height"> 키 :
        <input type="number" name="height">cm
      </label><br>

      <label for="weight"> 몸무게 :
        <input type="number" name="weight">kg
      </label><br>

      <label for="activity-num">활동량 :
        주 <input type="number" style="width: 20px;" name="activity-num">회
      </label>
      <label for="activity-hour">
        주 <input type="number" style="width: 20px;" name="activity-hour">시간 운동
      </label><br>

      <label for="goal"> 이번주 운동 목표
        <select name="radio" id="goal_type">
          <option value="11">유산소</option>
          <option value="22">헬스</option>
          <option value="33">스트레칭</option>
          <option value="44">맨몸운동</option>
          <option value="55">요가</option>
          <option value="66">필라테스</option>
          <option value="77">기타</option>
        </select>
        <input type="number" name="goal-hour"> 시간 <button class="btn">추가</button>
      </label><br>

      <label for="secret"> 내 정보 공개하기
        <select name="radio" id="secret">
          <option value="1">공개</option>
          <option value="2">비공개</option>
        </select>
      </label><br>

      <button class="btn btn-primary" >변경</button>
    </form>
    <button class="btn btn-secondary" >취소</button>
  </div>
</template>
<script>
export default {
  name: 'MyPageUpdateView',
  components: {},
  data() {
    return {
      user: {
        nick: '한나',
        slogan: '아자아자 화이팅!',
      },
    };
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {},
};
</script>

<style scoped>
  .profile-img {
    border-radius: 50%;
  }
</style>
