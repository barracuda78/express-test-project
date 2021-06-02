<%@ page import="ru.eforward.express_testing.model.Student" %>
<%@ page import="java.nio.file.Paths" %>
<%@ page import="ru.eforward.express_testing.dao.TestDAO" %>
<%@ page import="java.util.concurrent.atomic.AtomicReference" %>
<%@ page import="ru.eforward.express_testing.model.school.Test" %>
<%@ page import="java.util.List" %>
<%@ page import="java.nio.file.Path" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Тестирование</title>
    <link href="../../css/countdown.css" rel="stylesheet" type="text/css" >
    <!--script src="countdown.js"></script-->
</head>
<body>
    <h1>Тестирование</h1>


    <h2 class="countdown-title">Время тестирования:</h2>
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

        var deadline = new Date(Date.parse(new Date()) + 1 * 1 *  3 * 60 * 1000);
        initializeClock("countdown", deadline);
    </script>

    <%
        Student student = (Student)session.getAttribute("user");
        @SuppressWarnings("unchecked")
        final AtomicReference<TestDAO> testDao = (AtomicReference<TestDAO>) request.getServletContext().getAttribute("test");
        List<Test> tests = testDao.get().getTestsStore();   //Как тест привязан к студенту? Как они взаимосвязаны.
                                            // По этому принципу нужно вытащить из testDAO нужный тест, проверить, active  ли он, и запустить.
        Test currentTest = null;
        for(Test t : tests){
            if(t.isActive()){
                currentTest = t;
            }
        }

        if(currentTest != null){
            Path path = currentTest.getPath();
            String htmlString = student.performTest(Paths.get("D:\\coding\\projects\\EF\\express_test_project\\src\\main\\resources\\tests\\eng\\level01\\lesson01.txt"));

            %>
                <br/>
                <b><%="Тестирование началось:"%></b>
                <%=htmlString%>
            <%

        }
        if(currentTest == null){
            
            %>
                <b><%="Нет доступных тестов."%></b>
            <%

        }
    %>


</body>
</html>
