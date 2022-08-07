 <template>
  <div class="tabs">
    <div class="tab-menu d-flex">
      <b-button-group class="mx-1" v-for="(tab, index) in tabs" :key="index"
        v-bind:class="{ active: currentTab === index }">
        <b-button lg="4" size="lg" v-on:click="currentTab = index"
        variant="outline-primary">{{ tab }}
        </b-button>
      </b-button-group>
    </div>
    <div class="tab-content wrap">
      <div v-show="currentTab == 0" class="scroll__wrap">
        <div v-for="(room, i) in roomList" :key="i" class="scroll--element">
            <b-card :header="room" footer-tag="footer">
                <b-card-img src="https://placekitten.com/380/200"></b-card-img>
                <template #footer>
                  <b-button variant="outline" @click="joinMeeting(room)">들어가기</b-button>
                </template>
            </b-card>
        </div>
      </div>
    </div>
    <div class="tab-content wrap">
      <div v-show="currentTab == 1" class="scroll__wrap">
        <div v-for="(room, i) in roomList" :key="i" class="scroll--element">
            <b-card :header="room" footer-tag="footer">
                <b-card-img src="https://placekitten.com/380/200"></b-card-img>
                <template #footer>
                  <b-button variant="outline" @click="joinMeeting(room)">들어가기</b-button>
                </template>
            </b-card>
        </div>
      </div>
    </div>
    <div class="tab-content wrap">
      <div v-show="currentTab == 2" class="scroll__wrap">
        <div v-for="(room, i) in roomList" :key="i" class="scroll--element">
            <b-card :header="room" footer-tag="footer" class="scroll--element_sub">
                <b-card-img src="https://placekitten.com/380/200"></b-card-img>
                <template #footer>
                  <b-button variant="outline" @click="joinMeeting(room)">들어가기</b-button>
                </template>
            </b-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// eslint--disable-next-line
export default {
  name: 'myTabs',
  components: {
  },
  data() {
    return {
      currentTab: 0,
      tabs: ['운동방', '자유방', '경쟁방'],
      roomList: [1, 2, 3, 4, 5],
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

<style scope>
.tab-menu {
  margin-top: 50px;
}
/* 기본 구조 */
.wrap {
  max-width:100%;
  margin:50px auto;
  background:#f8f8f8;}
/* 가로 스크롤 적용 */
.scroll__wrap {overflow:auto; white-space:nowrap; font-size:15px; margin-top: -30px;}
.scroll--element {
  display: inline-block;
  line-height: 15px;
  width:33%;
  height:100%;
}
.scroll--element + scroll--element {margin-left:10px;}
</style>
