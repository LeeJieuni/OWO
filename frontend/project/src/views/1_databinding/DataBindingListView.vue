<template>
  <div>
    <div class="form-floating" id="select1">
      <select class="form-select" name="" id="">
        <option :value="city.code" :key="city.code" v-for="city in cities">{{ city.title }}</option>
      </select>
      <label for="select1">도시를 선택해 주세요.</label>
    </div>
    <div>
      <table class="table caption-top ">
        <caption>음료수 주문 정보</caption>
        <thead class="table-light">
          <tr>
            <th scope="col">제품번호</th>
            <th scope="col">제품명</th>
            <th scope="col">가격</th>
            <th scope="col">주문수량</th>
            <th scope="col">합계</th>
          </tr>
        </thead>
        <tbody>
          <tr :key="drink.drinkId" v-for="drink in drinkList">
            <td>{{ drink.drinkId }}</td>
            <td>{{ drink.drinkName }}</td>
            <td>{{ drink.price }}</td>
            <td><input type="number" v-model="drink.qty"></td>
            <td>{{drink.price * drink.qty}}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      data1: '',
      cities: [
        { title: '서울', code: '02' },
        { title: '부산', code: '051' },
        { title: '제주', code: '064' }
      ],
      drinkList: [
        {
          drinkId: '1',
          drinkName: '코카콜라',
          price: 700,
          qty: 1
        },
        {
          drinkId: '2',
          drinkName: '오렌지주스',
          price: 1200,
          qty: 1
        },
        {
          drinkId: '3',
          drinkName: '커피',
          price: 500,
          qty: 1
        },
        {
          drinkId: '4',
          drinkName: '물',
          price: 700,
          qty: 1
        },
        {
          drinkId: '5',
          drinkName: '보리차',
          price: 1200,
          qty: 1
        },
        {
          drinkId: '6',
          drinkName: '포카리',
          price: 1000,
          qty: 1
        },
        {
          drinkId: '7',
          drinkName: '뽀로로',
          price: 1300,
          qty: 1
        }
      ]
    }
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {}
}
</script>
