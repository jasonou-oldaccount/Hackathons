var myApp = angular.module('myApp', ['ngRoute']);

myApp.config(function ($routeProvider) {
    
    $routeProvider

    .when('/', {
        templateUrl: 'pages/start.htm',
        controller: 'mainController',
        css: 'style/start.css'
    })

    .when('/game', {
        templateUrl: 'pages/game.htm',
        controller: 'secondController',
        css: 'style/game.css'
    })
    
});

myApp.service('nameService', function() {
   
    var self = this;
    
    this.name1 = 'John Doe';
    this.school1 = 'JohnU';
    
    this.name2 = 'Jane Doe';
    this.school2 = 'JaneU';
    
    this.gender1 = 'male';
    this.gender2 = 'male';
    
    this.count = 0;
    
});

myApp.controller('keyController', ['$scope', 'nameService', function($scope, nameService) {
    
    
}]);

myApp.controller('mainController', ['$scope', 'nameService', function($scope, nameService) {
    
    $scope.$watch('name1', function() {
       nameService.name1 = $scope.name1; 
    });
    
    $scope.$watch('name2', function() {
       nameService.name2 = $scope.name2; 
    });
    
    $scope.$watch('school1', function() {
       nameService.school1 = $scope.school1; 
    });
    
    $scope.$watch('school2', function() {
       nameService.school2 = $scope.school2; 
    });
    
    $scope.$watch('school2', function() {
       nameService.school2 = $scope.school2; 
    });
    
    $scope.$watch('gender1', function() {
       nameService.gender1 = $scope.gender1; 
    });
    
    $scope.$watch('gender2', function() {
       nameService.gender2 = $scope.gender2; 
    });
}]);

myApp.controller('secondController', ['$scope', '$timeout', 'nameService', function($scope, $timeout, nameService) {
    
    $scope.gameover = 0;
    
    $scope.femaleattack1 = 0;
    $scope.femaleattack2 = 0;
    $scope.maleattack1 = 0;
    $scope.maleattack2 = 0;
    
    $scope.takedamage1 = 0;
    $scope.takedamage2 = 0;
    
    $scope.gender1 = nameService.gender1;
    $scope.gender2 = nameService.gender2;
    
    $scope.name1 = nameService.name1;
    
    $scope.$watch('name1', function() {
       nameService.name1 = $scope.name1; 
    });
    
    $scope.name2 = nameService.name2;
    
    $scope.$watch('name2', function() {
       nameService.name2 = $scope.name2; 
    });

    $scope.school1 = nameService.school1;
    
    $scope.$watch('school1', function() {
       nameService.school1 = $scope.school1; 
    }); 

    $scope.school2 = nameService.school2;
    
    $scope.$watch('school2', function() {
       nameService.school2 = $scope.school2; 
    });
    
    $scope.answered1 = false;
    $scope.answered2 = false;
    
    $scope.winner1 = 0;
    $scope.winner2 = 0;
    
    $scope.counter = 10;
    $scope.round = 0;
    $scope.onTimeout = function() {
        if($scope.counter == 0 && $scope.round != 4) {
            $scope.counter = 10;
            $scope.round++;
            $scope.answered1 = !$scope.answered1;
            $scope.answered2 = !$scope.answered2;
        } else if($scope.round == 4) {
            $timeout.cancel(mytimeout);
            if($scope.player1 > $scope.player2) {
                $scope.winner1++;
                $scope.winner2--;
            } else if($scope.player1 < $scope.player2) {
                $scope.winner1--;
                $scope.winner2++;
            }
        } else {
            if($scope.answered1 && $scope.answered2) {
                $scope.counter = 0;
            } else {
                $scope.counter--;
            }
        }

        if($scope.gender2 == 'male') {
            $scope.maleattack2 = 0;
        } else {
            $scope.femaleattack2 = 0;
        }
        
        if($scope.gender1 == 'male') {
            $scope.maleattack1 = 0;
        } else {
            $scope.femaleattack1 = 0;
        }
        
        $scope.takedamage2 = 0;
        $scope.takedamage1 = 0;
        
        mytimeout = $timeout($scope.onTimeout,1000);
    }
    var mytimeout = $timeout($scope.onTimeout,1000);
    
    var questions = ['What is 1 + 1?', 'What is 17 + 19?', 'What is 12 * 11?', 'What is 6 / 2?'];
    
    $scope.multiplechoice = [{ans:'132', id:132}, {ans:'2', id:2}, {ans:'3', id:3}, {ans:'36', id:36}];
    
    var answers = [2, 36, 132, 3];

    $scope.getQuestion = function(value) { 
        return questions[value]; 
    }
    
    $scope.player1 = 0;
    $scope.player2 = 0;
    
    $scope.hp = 100;
    $scope.hp1 = 100;
    
    $scope.combo1 = false;
    $scope.combo1 = false;
    
    $scope.checkAnswer = function(index, id) {
        if(!$scope.answered1) {
            if(id == answers[index] && $scope.hp1 != 0) {
                if($scope.gender2 == 'male') {
                    $scope.maleattack2 = 1;
                } else {
                    $scope.femaleattack2 = 1;
                }
                
                $scope.takedamage1 = 1;
                
                if($scope.combo2) {
                    $scope.player2 = $scope.player2 + 0.5;
                    $scope.hp1 = $scope.hp1 - 10;
                }
                $scope.hp1 = $scope.hp1 - 15;
                $scope.player2++;
                $scope.combo2 = true;
            }
            if(id != answers[index]) $scope.combo2 = false;
            if($scope.hp1 <= 0) { 
                $timeout.cancel(mytimeout);
                if($scope.player1 > $scope.player2) {
                    $scope.winner1++;
                    $scope.winner2--;
                } else if($scope.player1 < $scope.player2) {
                    $scope.winner1--;
                    $scope.winner2++;
                }
            }
            $scope.answered1 = !$scope.answered1;
        }
    }
    
    $scope.checkAnswer1 = function(index, id) {
        if(!$scope.answered2) {
            if(id == answers[index] && $scope.hp != 0) {
                
                if($scope.gender1 == 'male') {
                    $scope.maleattack1 = 1;
                } else {
                    $scope.femaleattack1 = 1;
                }
                
                $scope.takedamage2 = 1;
    
                if($scope.combo1) {
                    $scope.player1 = $scope.player1 + 0.5;
                    $scope.hp = $scope.hp - 10;
                }
                $scope.hp = $scope.hp - 15;
                $scope.player1++;
                $scope.combo1 = true;
            }
            if(id != answers[index]) $scope.combo1 = false;
            if($scope.hp1 <= 0) {
                $timeout.cancel(mytimeout);
                if($scope.player1 > $scope.player2) {
                    $scope.winner1++;
                    $scope.winner2--;
                } else if($scope.player1 < $scope.player2) {
                    $scope.winner1--;
                    $scope.winner2++;
                }
            }
            $scope.answered2 = !$scope.answered2;
        }
    }
    
    $scope.keyCode = '';
    $scope.keyPressed = function(e) {
        $scope.keyCode = e.which;
        var choice = -1;
        var plr = -1;
        if ($scope.keyCode == 97) { choice = 0; plr = 1; }
        if ($scope.keyCode == 115) { choice = 1; plr = 1; }
        if ($scope.keyCode == 100) { choice = 2; plr = 1; }
        if ($scope.keyCode == 102) { choice = 3; plr = 1; }
        if ($scope.keyCode == 104) { choice = 0; plr = 2; }
        if ($scope.keyCode == 106) { choice = 1; plr = 2; }
        if ($scope.keyCode == 107) { choice = 2; plr = 2; }
        if ($scope.keyCode == 108) { choice = 3; plr = 2; }
        
        if (choice != -1) {
            if (plr == 2) $scope.checkAnswer($scope.round, $scope.multiplechoice[choice].id);
            if (plr == 1) $scope.checkAnswer1($scope.round, $scope.multiplechoice[choice].id);
        }
    };
    
    $scope.fights = [{name1: 'None', score1: 0, name2: 'None', score2: 0}, {name1: 'None', score1: 0, name2: 'None', score2: 0}, {name1: 'None', score1: 0, name2: 'None', score2: 0}, {name1: 'None', score1: 0, name2: 'None', score2: 0}, {name1: 'None', score1: 0, name2: 'None', score2: 0}, {name1: 'None', score1: 0, name2: 'None', score2: 0}, {name1: 'None', score1: 0, name2: 'None', score2: 0}, {name1: 'None', score1: 0, name2: 'None', score2: 0}, {name1: 'None', score1: 0, name2: 'None', score2: 0}, {name1: 'None', score1: 0, name2: 'None', score2: 0}, {name1: 'None', score1: 0, name2: 'None', score2: 0}, {name1: 'None', score1: 0, name2: 'None', score2: 0}]
    
}]);

myApp.directive('shortcut', function() {
    return {
        restrict: 'E',
        replace: true,
        scope: true,
        link: function postLink(scope, iElement, iAttrs){
            jQuery(document).on('keypress', function(e){
                scope.$apply(scope.keyPressed(e));
            });
        }
    };
});
myApp.directive('maleright', function() {
    return {
        restrict: 'AE',
        template: '<img src="images/male_right.png" alt="character two" height="50%" width="50%" class="shakeimage" onMouseover="init(this);rattleimage()" onMouseout="stoprattle(this);top.focus()" onClick="top.focus()">',
        replace: true
    }
});

myApp.directive('maleleft', function() {
    return {
        restrict: 'AE',
        template: '<img src="images/male_left.png" alt="character two" height="50%" width="50%" class="shakeimage" onMouseover="init(this);rattleimage()" onMouseout="stoprattle(this);top.focus()" onClick="top.focus()">',
        replace: true
    }
});

myApp.directive('femaleright', function() {
    return {
        restrict: 'AE',
        template: '<img src="images/female_right.png" alt="character two" height="50%" width="50%" class="shakeimage" onMouseover="init(this);rattleimage()" onMouseout="stoprattle(this);top.focus()" onClick="top.focus()">',
        replace: true
    }
});

myApp.directive('femaleleft', function() {
    return {
        restrict: 'AE',
        template: '<img src="images/female_left.png" alt="character two" height="50%" width="50%" class="shakeimage" onMouseover="init(this);rattleimage()" onMouseout="stoprattle(this);top.focus()" onClick="top.focus()">',
        replace: true
    }
});

myApp.directive('malerighthit', function() {
    return {
        restrict: 'AE',
        template: '<img src="images/hit_right_m.gif" alt="character two" height="50%" width="50%" class="shakeimage" onMouseover="init(this);rattleimage()" onMouseout="stoprattle(this);top.focus()" onClick="top.focus()">',
        replace: true
    }
});

myApp.directive('malelefthit', function() {
    return {
        restrict: 'AE',
        template: '<img src="images/hit_left_m.gif" alt="character two" height="50%" width="50%" class="shakeimage" onMouseover="init(this);rattleimage()" onMouseout="stoprattle(this);top.focus()" onClick="top.focus()">',
        replace: true
    }
});

myApp.directive('femalerighthit', function() {
    return {
        restrict: 'AE',
        template: '<img src="images/hit_right_f.gif" alt="character two" height="50%" width="50%" class="shakeimage" onMouseover="init(this);rattleimage()" onMouseout="stoprattle(this);top.focus()" onClick="top.focus()">',
        replace: true
    }
});

myApp.directive('femalelefthit', function() {
    return {
        restrict: 'AE',
        template: '<img src="images/hit_left_f.gif" alt="character two" height="50%" width="50%" class="shakeimage" onMouseover="init(this);rattleimage()" onMouseout="stoprattle(this);top.focus()" onClick="top.focus()">',
        replace: true
    }
});

myApp.directive('malerightgethit', function() {
    return {
        restrict: 'AE',
        template: '<img src="images/male_right_gethit.gif" alt="character two" height="50%" width="50%" class="shakeimage" onMouseover="init(this);rattleimage()" onMouseout="stoprattle(this);top.focus()" onClick="top.focus()">',
        replace: true
    }
});

myApp.directive('maleleftgethit', function() {
    return {
        restrict: 'AE',
        template: '<img src="images/male_left_gethit.gif" alt="character two" height="50%" width="50%" class="shakeimage" onMouseover="init(this);rattleimage()" onMouseout="stoprattle(this);top.focus()" onClick="top.focus()">',
        replace: true
    }
});

myApp.directive('femalerightgethit', function() {
    return {
        restrict: 'AE',
        template: '<img src="images/female_right_gethit.gif" alt="character two" height="50%" width="50%" class="shakeimage" onMouseover="init(this);rattleimage()" onMouseout="stoprattle(this);top.focus()" onClick="top.focus()">',
        replace: true
    }
});

myApp.directive('femaleleftgethit', function() {
    return {
        restrict: 'AE',
        template: '<img src="images/female_left_gethit.gif" alt="character two" height="50%" width="50%" class="shakeimage" onMouseover="init(this);rattleimage()" onMouseout="stoprattle(this);top.focus()" onClick="top.focus()">',
        replace: true
    }
});
