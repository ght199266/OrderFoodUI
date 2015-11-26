package com.lly.order.entity;

import java.util.List;

/**
 * FoodEntity[v 1.0.0]
 * classes:com.lly.order.entity.FoodEntity
 *
 * @author lileiyi
 * @date 2015/11/20
 * @time 13:20
 * @description
 */
public class FoodEntity {

    private List<category> categoryList;

    public List<category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<category> categoryList) {
        this.categoryList = categoryList;
    }


    public static class category {
        private String name;
        private List<foodbean> foodbeans;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<foodbean> getFoodbeans() {
            return foodbeans;
        }

        public void setFoodbeans(List<foodbean> foodbeans) {
            this.foodbeans = foodbeans;
        }


        public static class foodbean {
            private String dishes;
            private int count;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getDishes() {
                return dishes;
            }

            public void setDishes(String dishes) {
                this.dishes = dishes;
            }
        }
    }

}
