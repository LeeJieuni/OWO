<template>
  <div class="row">
    <Picker :data="emojiIndex" set="google" @select="showEmoji" />
  </div>
</template>

<script>
// Import data/twitter.json to reduce size, all.json contains data for
// all emoji sets.
import data from 'emoji-mart-vue-fast/data/all.json';
// Import default CSS
import 'emoji-mart-vue-fast/css/emoji-mart.css';

// Vue 3, import components from `/src`:
import { Picker, EmojiIndex } from 'emoji-mart-vue-fast/src';

// Create emoji data index.
// We can change it (for example, filter by category) before passing to the component.
const emojiIndex = new EmojiIndex(data);

export default {
  name: 'App',
  components: {
    Picker,
  },

  data() {
    return {
      emojiIndex,
      emojisOutput: '',
    };
  },

  methods: {
    showEmoji(emoji) {
      this.emojisOutput += emoji.native;
    },
  },
};
</script>

<style>
.row { display: flex; }
.row > * { margin: auto; }
</style>
