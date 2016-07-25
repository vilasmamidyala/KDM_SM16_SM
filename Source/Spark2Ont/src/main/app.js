'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [])


    .controller('View1Ctrl', function ($scope, $http) {
      $scope.venueList = new Array();
      $scope.mostRecentReview;
      $scope.getVenues = function () {
        var placeEntered = document.getElementById("txt_CompName").value;

        if (placeEntered != null && placeEntered != "" ) {
          document.getElementById('div_ReviewList').style.display = 'none';
          //This is the API that gives the list of venues based on the place and search query.
          var handler = $http.get("http://api.intellexer.com/summarize?apikey=bbbfb162-ee2e-4c5b-8945-45ded0377a98&url=http%3A%2F%2Fwww.cricinfo.com&summaryRestriction=7&returnedTopicsCount=2&loadConceptsTree=true&loadNamedEntityTree=true&usePercentRestriction=true&conceptsRestriction=7&structure=general&fullTextTrees=true&textStreamLength=1000&useCache=false&wrapConcepts=true");
          handler.success(function (data) {

              if (data != null && data.def[0] != null && data.def[0].tr != undefined && data.def[0].tr != null) {
                  for (var i = 0; i < 5; i++) {
                      $scope.venueList[i] = {
                          "text": data.def[0].tr[i].text
                      };
                  }
              }

          })
          handler.error(function (data) {
            alert("There was some error processing your request. Please try after some time.");
          });
        }
      }
