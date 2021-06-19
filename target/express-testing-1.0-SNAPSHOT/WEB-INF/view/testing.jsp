<%@ page import="ru.eforward.express_testing.model.Student" %>
<%@ page import="java.nio.file.Paths" %>
<%@ page import="ru.eforward.express_testing.dao.TestDAOFakeDatabaseImpl" %>
<%@ page import="java.util.concurrent.atomic.AtomicReference" %>
<%@ page import="ru.eforward.express_testing.model.school.Test" %>
<%@ page import="java.util.List" %>
<%@ page import="java.nio.file.Path" %>
<%@ page import="ru.eforward.express_testing.testingProcess.TestingUnit" %>
<%@ page import="ru.eforward.express_testing.utils.LogHelper" %>
<%@ page import="java.util.Optional" %>
<%@ page import="ru.eforward.express_testing.utils.CloneMaker" %>
<%@ page import="java.util.Objects" %>
<%@ page import="ru.eforward.express_testing.testingProcess.Stopper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Тестирование</title>
    <link href="../../css/countdown.css" rel="stylesheet" type="text/css" >
    <!--script src="countdown.js"></script-->
</head>
<body>
    <h1>Тестирование</h1>

    <%
        LogHelper.writeMessage("---class testing.jsp : user entered.");

        Student student = (Student)session.getAttribute("user");
        ServletContext servletContext = request.getServletContext();
        @SuppressWarnings("unchecked")
        AtomicReference<List<TestingUnit>> testingUnitsListAtomicReference = (AtomicReference<List<TestingUnit>>)servletContext.getAttribute("testingUnitsListAtomicReference");
        List<TestingUnit> list = null;

        //find out if there is an available test (TestingUnit) for this student (his group);
        boolean testIsAvailable = false;
        if(testingUnitsListAtomicReference != null && student != null){
            list = testingUnitsListAtomicReference.get();
            testIsAvailable = list
                    .stream()
                    .anyMatch(testingUnit -> {
                        //here we should take testingUnit from list and run it -> html code!
                        return testingUnit.getGroupId() == student.getGroupId();
                    });
        }

        if(testIsAvailable){

            //get the first available test (TestingUnits) for this student (his group):
            Optional<TestingUnit> optionalTestingUnit = list
                    .stream()
                    .filter(testingUnit -> testingUnit.getGroupId() == student.getGroupId())
                    .findFirst();

            //get the available first/next question of this test (of this TestingUnit)
            String htmlString = "Извините. Файл с тестом не был подготовлен.";
            TestingUnit studentsTestingUnit = (TestingUnit)session.getAttribute("studentsTestingUnit");



            if(studentsTestingUnit == null && optionalTestingUnit.isPresent()){
                TestingUnit testingUnit = optionalTestingUnit.get();
                //clone this object, and pass it to sessionAttribute after it.
                TestingUnit clone = CloneMaker.getClone(testingUnit);
                //add cloned TestingUnit to student's own session:
                session.setAttribute("studentsTestingUnit", clone);
                studentsTestingUnit = clone;
            }


            long millisPassed = 0L; //used for setting up js timer every time;
            double duration = 0.0d;
            //pull next question from TestingUnit: (iteration algorithm exists in TestingUnit entity)
            if(studentsTestingUnit != null && studentsTestingUnit.hasNextTest()){
                //double dur = studentsTestingUnit.getDuration(); //used for setting up js timer every time;
                //get stopper for testing from student's session attributes if exists, otherwise create new Stopper():
                Stopper stopper = (Stopper)session.getAttribute("stopper");
                //get duration from testingUnit:
                duration = studentsTestingUnit.getDuration();
                if(Objects.isNull(stopper)){
                    stopper = new Stopper(duration);
                    session.setAttribute("stopper", stopper);
                }
                millisPassed = stopper.getMillisPassed();
                htmlString = studentsTestingUnit.getNextTest();
                LogHelper.writeMessage("T E S T I N G . J S P : dur = " + duration);
//                int durationInteger = (int)dur;
//                LogHelper.writeMessage("T E S T I N G . J S P : durationInteger = " + durationInteger);
            }


    %>
    <br/>
    <!--This is for countdown:-->
    <h2 class="countdown-title">Тестирование началось:</h2>
    <div id="deadline-message" class="deadline-message">
        Время тестирования закончилось!
    </div>
    <div id="countdown" class="countdown">
        <!--div class="countdown-number">
            <span class="days countdown-time"></span>
            <span class="countdown-text">Days</span>
        </div>
        <div class="countdown-number">
            <span class="hours countdown-time"></span>
            <span class="countdown-text">Hours</span>
        </div-->
        <div class="countdown-number">
            <span class="minutes countdown-time"></span>
            <span class="countdown-text">Минуты</span>
        </div>
        <div class="countdown-number">
            <span class="seconds countdown-time"></span>
            <span class="countdown-text">Секунды</span>
        </div>
    </div>
    <script>
        function getTimeRemaining(endtime) {
            var t = Date.parse(endtime) - Date.parse(new Date());
            var seconds = Math.floor((t / 1000) % 60);
            var minutes = Math.floor((t / 1000 / 60) % 60);
            //var hours = Math.floor((t / (1000 * 60 * 60)) % 24);
            //var days = Math.floor(t / (1000 * 60 * 60 * 24));
            return {
                total: t,
                //days: days,
                //hours: hours,
                minutes: minutes,
                seconds: seconds
            };
        }

        function initializeClock(id, endtime) {
            var clock = document.getElementById(id);
            //var daysSpan = clock.querySelector(".days");
            //var hoursSpan = clock.querySelector(".hours");
            var minutesSpan = clock.querySelector(".minutes");
            var secondsSpan = clock.querySelector(".seconds");

            function updateClock() {
                var t = getTimeRemaining(endtime);

                if (t.total <= 0) {
                    document.getElementById("countdown").className = "hidden";
                    document.getElementById("deadline-message").className = "visible";
                    clearInterval(timeinterval);
                    return true;
                }

                //daysSpan.innerHTML = t.days;
                //hoursSpan.innerHTML = ("0" + t.hours).slice(-2);
                minutesSpan.innerHTML = ("0" + t.minutes).slice(-2);
                secondsSpan.innerHTML = ("0" + t.seconds).slice(-2);
            }

            updateClock();
            var timeinterval = setInterval(updateClock, 1000);
        }
        var duratio ="<%=duration%>"
        var studentmillisPassed="<%=millisPassed%>"
        var deadline = new Date(Date.parse(new Date()) + 1 * 1 * duratio * 60 * 1000 - studentmillisPassed);
        initializeClock("countdown", deadline);
    </script>
    <!--end of countdown code-->

     <%=htmlString%>

    <%
        //httpSession.setAttribute("nfe", "nfe");
        String numberFormatException = (String)session.getAttribute("nfe");
        if(Objects.nonNull(numberFormatException) && "nfe".equals(numberFormatException)){
            %>
                <%="Вы указали число в некорректном формате. Введите число в формате 5.23 или в формате 25"%>
            <%
        }
    %>

     <%
        }
        if(!testIsAvailable){
            %>
                <p><%="Нет доступных тестов."%></p>
            <%
        }
    %>


</body>
</html>
