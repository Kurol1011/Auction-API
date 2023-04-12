Vue.component('item-1',{
    props: ['todo'],
    template: '<li>{{todo.text}}</li>'
})

var app = new Vue({
    el: '#app',
    data: {
       days:[
           {text: "Monday"},
           {text: "Tuesday"},
           {text: "Wednesday"},
           {text: "Thursday"},
           {text: "Friday"},
           {text: "Saturday"},
           {text: "Sunday"}
       ],
        seen: true,
        message: "text",
        firstName:"Josh",
        lastName:"Tyler",
        nek:"text" ,
        ska:"test"
        //fullName:"Josh Tyler"
    },
    methods: {
        deleteOneMessage: function(){
            this.days.pop();
            if(this.days.length == 0){
                this.seen = false;
            }
        }
    },
    computed: {
        fullName: {
            get: function () {
                return this.firstName + " " + this.lastName;
            },
            set: function(newVal){
              let names = newVal.split(' ');
              this.firstName = names[0];
              this.lastName = names[1];
            }
        },

    },
    watch: {
        nek: { function() {
                    this.ska = Date.now();
                }
        }
    }
});

