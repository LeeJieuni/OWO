<template>
    <div class="achat d-flex justify-content-center align-items-center">
        <form class="d-flex align-items-center achat-submit" id="chattingform">
          <label class="m-0" for="chatting"><textarea style="resize:none; border-radius:10px;"
          id="chatting" name="chatting" rows="2" cols="25"></textarea></label>
          <button type="submit" class="btn btn-light achat-submit2" form="chatting">
            Send</button>
        </form>
      <div class="fluid achat-content">
      </div>
    </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
    };
  },
  setup() {},
  created() {},
  moundted() {},
  unmounted() {},
  methods: {},
};
</script>
<style scoped>
.achat {
  position:fixed;
  border: 2px solid #4e8aff;
  border-radius: 30px;
  width:320px;
  height:600px;
  background-color: #4e8aff;
  bottom: 100px;
  right: 20px;
}

.achat-content {
  position:fixed;
  border: 2px solid #4e8aff;
  width:320px;
  height:490px;
  background-color: white;
  bottom: 180px;
  right: 20px;
}

.achat-submit {
  position:fixed;
  width:300px;
  height:70px;
  bottom: 105px;
  right: 19px;
}

.achat-submit2 {
  position: fixed;
  bottom: 124px;
  right: 40px;
}
</style>
