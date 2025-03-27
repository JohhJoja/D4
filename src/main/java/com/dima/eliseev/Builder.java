package com.dima.eliseev;

public class Builder {
    private int age;
    private String name;
    private String lastname;
    private int weight;

    Builder(BuilderPattern builderPattern){
        this.age = builderPattern.age;
        this.name = builderPattern.name;
        this.lastname = builderPattern.lastname;
        this.weight = builderPattern.weight;
    }

    static class BuilderPattern{
        private int age;
        private String name;
        private String lastname;
        private int weight;

        public BuilderPattern BuilderPatternSetAge(int age){
            this.age = age;
            return this;
        }
        public BuilderPattern BuilderPatternSetName(String name){
            this.name = name;
            return this;
        }
        public BuilderPattern BuilderPatternSetLastname(String lastname){
            this.lastname = lastname;
            return this;
        }
        public BuilderPattern BuilderPatternSetWeight(int weight){
            this.weight = weight;
            return this;
        }
        public Builder build(){
            return new Builder(this);
        }
    }
}
