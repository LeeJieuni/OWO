 <template>
  <div class="tabs">
    <div class="tab-menu d-flex">
        <b-button-group class="mx-1" v-for="(tab, index) in tabs" :key="index"
          v-bind:class="{ active: currentTab === index }">
          <b-button lg="4" size="lg" href="" v-on:click="currentTab = index"
          variant="outline-primary">{{ tab }}</b-button>
        </b-button-group>
    </div>
    <div class="tab-content">
      <div v-show="currentTab == 0">
        <b-card-group deck>
          <b-card header="방제목" class="rounded-5" footer-tag="footer">
            <b-card-img src="https://picsum.photos/600/200/?image=25" style="height: 300px">썸네일</b-card-img>
            <template #footer>
              <b-button href="#" variant="outline">들어가기</b-button>
            </template>
          </b-card>
          <b-card header="방제목" class="rounded-5" footer-tag="footer">
            <b-card-img src="https://picsum.photos/600/200/?image=25" style="height: 300px">썸네일</b-card-img>
            <template #footer>
              <b-button href="#" variant="outline">들어가기</b-button>
            </template>
          </b-card>
          <b-card header="방제목" class="rounded-5" footer-tag="footer">
            <b-card-img src="https://picsum.photos/600/200/?image=21" style="height: 300px">썸네일</b-card-img>
            <template #footer>
              <b-button variant="outline">들어가기</b-button>
            </template>
          </b-card>
        </b-card-group>
      </div>
    </div>
    <div v-show="currentTab == 1">
      <b-card-group deck>
        <b-card header="방제목" class="rounded-5" footer-tag="footer">
          <b-card-text>썸네일</b-card-text>
          <template #footer>
            <b-button href="#" variant="outline">들어가기</b-button>
          </template>
        </b-card>
        <b-card header="방제목" class="rounded-5" footer-tag="footer">
          <b-card-text>썸네일</b-card-text>
          <template #footer>
            <b-button href="#" variant="outline">들어가기</b-button>
          </template>
        </b-card>
        <b-card header="방제목" class="rounded-5" footer-tag="footer">
          <b-card-text>썸네일</b-card-text>
          <template #footer>
            <b-button variant="outline">들어가기</b-button>
          </template>
        </b-card>
      </b-card-group>
    </div>
    <div v-show="currentTab == 2">
      <b-card-group deck>
        <b-card header="방제목" class="rounded-5" footer-tag="footer">
          <b-card-text>썸네일</b-card-text>
          <template #footer>
            <b-button href="#" variant="outline">들어가기</b-button>
          </template>
        </b-card>
        <b-card header="방제목" class="rounded-5" footer-tag="footer">
          <b-card-text>썸네일</b-card-text>
          <template #footer>
            <b-button href="#" variant="outline">들어가기</b-button>
          </template>
        </b-card>
        <b-card header="방제목" class="rounded-5" footer-tag="footer">
          <b-card-text>썸네일</b-card-text>
          <template #footer>
            <b-button variant="outline">들어가기</b-button>
          </template>
        </b-card>
      </b-card-group>
    </div>
  </div>
  <button v-for="(room, i) in roomList" :key="i" @click="joinMeeting(room)">
    <div>
      {{ room }}
    </div>
    <div>
      이 방에 들어가시겠습니까?
    </div>
  </button>
</template>

<script>
// eslint--disable-next-line
export default {
  name: 'myTabs',
  components: {
  },
  data() {
    return {
      roomList: ['hello1', 'hello2', 'hello3'],
    };
  },
  computed: {
  },
  methods: {
    joinMeeting(sessionNum) {
      console.log(sessionNum);
      this.$router.push(`/room/competition/${sessionNum}`);
    },
  },
};
</script>

<style>
.tab-menu {
  margin-top: 10px;
  margin-bottom: 15px;
}
</style>
