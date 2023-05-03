
    let targetId;
    let host = 'http://' + window.location.host;

    $(document).ready(function () {
    $('#cards-box').empty();
    // cookie 여부 확인하여 로그인 확인
    const auth = getToken();

    // 처음 로딩 시 사용자 정보 가져오기 (이름 및 폴더)
    if(auth !== '') {
    // 로그인한 유저 이름
    $.ajax({
    type: 'GET',
    url: `/api/user-info`,
    beforeSend: function (xhr) {
    xhr.setRequestHeader("Authorization", auth);
},
    success: function (response) {
    if (response === 'fail') {
    logout();
    window.location.reload();
} else {
    $('#user').text(response);
    $('#writer').text(response);
    $('#commentwriter').val(response);
}
},
    error(error, status, request) {
    console.error(error);
    logout();
    window.location.href = host + "/api/user/login-page";
}
});
    $('#login-true').show();
    $('#login-false').hide();
} else {
    $('#login-false').show();
    $('#login-true').hide();
}

})


    function login() {

    let username = $('#sign-in-id-Box').val();
    let password = $('#sign-in-pw-Box').val();

    if (username == '') {
    alert('ID를 입력해주세요');
    return;
} else if(password == '') {
    alert('비밀번호를 입력해주세요');
    return;
}

            $.ajax({
            type: "POST",
            url: `/api/user/login`,
            contentType: "application/json",
            data: JSON.stringify({username: username, password: password}),
            success: function (response, status, xhr) {
            if(response === 'success') {
            let host = window.location.host;
            let url = host + '/api/board/1';

            document.cookie =
            'Authorization' + '=' + xhr.getResponseHeader('Authorization') + ';path=/';
            window.location.href = 'http://' + url;
        } else {
            alert('로그인에 실패하셨습니다. 다시 로그인해 주세요.')
            window.location.reload();
        }
        }
        })
}

    function onclickAdmin() {
    // Get the checkbox
    var checkBox = document.getElementById("admin-check");
    // Get the output text
    var box = document.getElementById("admin-token");

    // If the checkbox is checked, display the output text
    if (checkBox.checked == true){
    box.style.display = "block";
} else {
    box.style.display = "none";
}
}

    function register(){
    $('#signForm').submit();
}


    function logout(check) {
    // 토큰 값 ''으로 덮어쓰기
    document.cookie =
        'Authorization' + '=' + '' + ';path=/';
    if(check) {
    window.location.reload();
}
}

    function  getToken() {
    let cName = 'Authorization' + '=';
    let cookieData = document.cookie;
    let cookie = cookieData.indexOf('Authorization');
    let auth = '';
    if(cookie !== -1){
    cookie += cName.length;
    let end = cookieData.indexOf(';', cookie);
    if(end === -1)end = cookieData.length;
    auth = cookieData.substring(cookie, end);
    }

    // kakao 로그인 사용한 경우 Bearer 추가
    if(auth.indexOf('Bearer') === -1 && auth !== ''){
    auth = 'Bearer ' + auth;
    }

    return auth;
}

    function writePost(){

        const auth = getToken();

        let categoryVal = $('#cateBox').val();
        let titleVal = $('#titleBox').val();
        let urlVal = $('#urlBox').val();
        let commentVal = $('#commentBox').val();

        $.ajax({
            type: 'POST',
            url: `/api/posts`,
            contentType: "application/json",
            data: JSON.stringify({boardId: categoryVal,
                                        title: titleVal,
                                        image_url: urlVal,
                                        content: commentVal}),
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", auth);
            },
            success: function (response) {
                alert('게시글 등록 성공!');
              window.location.reload();
            },
            error(error, status, request) {
                console.error(error);
                alert('게시글 등록 실패!');
                window.location.href = host + "/api/user/login-page";
            }
        });


    }
    // =============회원가입==============
    $('#sign-up').on('click', function () {
        $('.sign-up-black-bg').addClass('show-modal');
    });
    $('.sign-x-btn').on('click', function () {
        $('.sign-up-black-bg').removeClass('show-modal');
        window.location.reload();
    });

    $('#sign-up-send').on('click', function (e) {
        if ($('#sign-ni-Box').val() === '') {
            alert('닉네임을 입력해주세요')
            e.preventDefault()
        }
        if ($('#sign-id-Box').val() === '') {
            alert('아이디를 입력해주세요')
            e.preventDefault()
        }
        if ($('#sign-pw-Box').val() === '') {
            alert('비밀번호를 입력해주세요')
            e.preventDefault()
        }
    })

    // ==================로그인=========================
    $('#sign-in').on('click', function () {
        $('.sign-in-black-bg').addClass('show-modal');
    });
    $('.sign-in-x-btn').on('click', function () {
        $('.sign-in-black-bg').removeClass('show-modal');
        window.location.reload();
    });

    $('#sign-in-send').on('click', function (e) {
        if ($('#sign-in-id-Box').val() === '') {
            alert('아이디를 입력해주세요')
            e.preventDefault()
        }
        if ($('#sign-in-pw-Box').val() === '') {
            alert('비밀번호를 입력해주세요')
            e.preventDefault()
        }
    })
    // 취향공유=========================
    $('.write').on('click', function () {
        $('.black-bg').addClass('show-modal');
    });
    $('.close-btn').on('click', function () {
        $('.black-bg').removeClass('show-modal');
        window.location.reload();
    });
    // 카테고리 내비바===================
    $('#category-1').hover(function () {
        $('.circle1').addClass('circle-show');
    }, function () {
        $('.circle1').removeClass('circle-show')
    });

    $('#category-2').hover(function () {
        $('.circle2').addClass('circle-show');
    }, function () {
        $('.circle2').removeClass('circle-show')
    });

    $('#category-3').hover(function () {
        $('.circle3').addClass('circle-show');
    }, function () {
        $('.circle3').removeClass('circle-show')
    });

    $('#category-4').hover(function () {
        $('.circle4').addClass('circle-show');
    }, function () {
        $('.circle4').removeClass('circle-show')
    });

    $('#category-5').hover(function () {
        $('.circle5').addClass('circle-show');
    }, function () {
        $('.circle5').removeClass('circle-show')
    });
