<template>
  <div id="tabs">
    <ul class="tab-menu">
      <li v-for="(tab, index) in tabs" :key="index" v-bind:class="{ active: currentTab === index }">
        <button href="" v-on:click="currentTab = index">{{ tab }}</button>
      </li>
    </ul>
    <div class="tab-content d-flex row">
      <div v-show="currentTab == 0">
        <div id="tab-1" class="tab-content current">
          <b-card header="featured" header-tag="header" footer="Card Footer"
          footer-tag="footer" title="Title">
            <b-card-text>Header and footers using props.</b-card-text>
            <b-button href="#" variant="primary">Go somewhere</b-button>
          </b-card>
          <div id="tab-1" class="tab-content current">
            <b-card header="featured" header-tag="header" footer="Card Footer"
            footer-tag="footer" title="Title">
              <b-card-text>Header and footers using props.</b-card-text>
              <b-button href="#" variant="primary">Go somewhere</b-button>
            </b-card>
          </div>
          <div id="tab-1" class="tab-content current">
            <b-card header="featured" header-tag="header" footer="Card Footer"
            footer-tag="footer" title="Title">
              <b-card-text>Header and footers using props.</b-card-text>
              <b-button href="#" variant="primary">Go somewhere</b-button>
            </b-card>
          </div>
        </div>
      </div>
      <div v-show="currentTab == 1"> 1</div>
      <div v-show="currentTab == 2"> 2</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'myTabs',
  data() {
    return {
      currentTab: 0,
      tabs: ['운동방', '자유방', '경쟁방'],
    };
  },
};
</script>
