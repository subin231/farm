
const rePass = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{5,16}$/;
const reName = /^[가-힣]{2,10}$/
const reNick = /^[a-zA-Zㄱ-힣0-9][a-zA-Zㄱ-힣0-9]*$/;
const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const reHp = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;


// 유효성 검사 함수: 각 검증 변수를 확인하여 전체 폼의 유효성을 반환
function isFormOk() {
    if (isNameOk && isNickOk && isEmailOk && isHpOk) {
        return true;  // 모든 필드가 유효할 경우
    } else {
        return false;  // 하나라도 유효하지 않을 경우
    }
}

let isPassOk = false;
let isNameOk = true;
let isNickOk = true;
let isEmailOk = true;
let isHpOk = true;

window.onload=function () {

    const modifyForm = document.getElementsByTagName('form')[0];

    const btn_modify = document.querySelector('.btn_modify');
    const resultPass = document.querySelector('.resultPass')
    const btnPass2 = document.querySelector('.pass2');
    const btn_submit = document.querySelector('.btn_submit');
    const btn_duplicate = document.querySelector('.btn_duplicate');
    const btn_quit = document.querySelector('.btn_quit');

    //비밀번호 유효성 검사
    btnPass2.addEventListener('focusout', function () {
        const pass = document.querySelector('.userPass').value;
        const pass2 = document.querySelector('.pass2').value;

        if (!pass.match(rePass)) {
            resultPass.innerText = "비밀번호가 유효하지 않습니다.";
            resultPass.style.color = 'red';
            return;
        }

        if (pass == pass2) {
            resultPass.innerText = "비밀번호가 일치합니다.";
            resultPass.style.color = 'green';
            isPassOk = true;
        } else {
            resultPass.innerText = "비밀번호가 일치하지 않습니다.";
            resultPass.style.color = 'red';
            isPassOk = false;
        }
    });

    //비밀번호 수정
    btn_modify.addEventListener('click', async function (e) {
        e.preventDefault();

        const pass = document.querySelector('.userPass').value;
        const cate = 'pass';


        // 비밀번호 유효성 검사 완료 여부
        if (!isPassOk) {
            alert('비밀번호가 유효하지 않습니다.');
            return;
        }

        const jsonData = {
            "userUid": uid,
            "userPass": pass
        }

        const data = await fetchPost(`/userInfo/UserMyinfo`, jsonData);

        console.log(data);
        if (data) {
            alert('비밀번호가 정상적으로 변경되었습니다.');
        } else {
            alert('비밀번호를 다시 확인해주세요.');
        }

        const passfeild = document.querySelector('.userPass');
        const pass2feild = document.querySelector('.pass2');

        passfeild.value = '';
        pass2feild.value = '';
        resultPass.innerText = '';

    });

    //유효성검사
    const resultName = document.querySelector('.resultName');
    const resultNick = document.querySelector('.resultNick');
    const resultHp = document.querySelector('.resultHp');

    let userName = modifyForm.userName.value
    let userNick = modifyForm.userNick.value
    let userEmail = modifyForm.userEmail.value
    let userHp = modifyForm.userHp.value
    let userZip = modifyForm.userZip.value
    let userAddr1 = modifyForm.userAddr1.value
    let userAddr2 = modifyForm.userAddr2.value

    // 3.이름 유효성 검사
    modifyForm.userName.addEventListener('focusout', function () {
        userName = modifyForm.userName.value;

        if (!userName.match(reName)) {
            showResultInvalid(resultName, '이름이 유효하지 않습니다.');
            isNameOk = false;
        } else {
            resultName.innerText = "";
            isNameOk = true;
        }
    });

    modifyForm.userNick.addEventListener('focusout', function () {
        userNick = modifyForm.userNick.value;
        isNickOk = false;
        if (!userNick.match(reNick)) {
            resultNick.innerText = '별명이 유효하지 않습니다.';
            resultNick.style.color = 'red';
        }
    });

    // 4.별명 유효성 검사
    btn_duplicate.addEventListener('click', async function () {
        userNick = modifyForm.userNick.value;
        const type = modifyForm.btnnick.dataset.type;
        const value = userNick;

        const data = await fetchGet(`/user/UserRegister/${type}/${value}`)
        console.log(data.result)

        if (data.result > 0) {
            resultNick.innerText = '이미 사용중인 별명입니다.';
            resultNick.style.color = 'red';
            isNickOk = false;
        } else {
            resultNick.innerText = '사용 가능한 별명입니다.';
            resultNick.style.color = 'green';
            isNickOk = true;
        }
    })


    // 이메일 유효성 검사

    const btnSendEmail = document.querySelector('#btnSendEmail')
    const btnAuthEmail = document.getElementById('btnAuthEmail');

    const resultEmail = document.querySelector('.resultEmail');
    const auth = document.getElementsByClassName('auth')[0];


    btnSendEmail.addEventListener('click', async function () {

        userEmail = modifyForm.userEmail.value;
        const type = modifyForm.btnEmail.dataset.type;
        const value = userEmail;

        if (!value.match(reEmail)) {
            resultEmail.innerText = '이메일 형식이'
            showResultInvalid(resultEmail, '이메일 형식이 맞지 않습니다.');

            return;
        } else {
            resultEmail.innerText = '이메일 형식 유효';
        }

        const data = await fetchGet(`/user/UserRegister/${type}/${value}`);

        if (data.result > 0) {
            showResultInvalid(resultEmail, '이미 사용중인 이메일 입니다.');
        } else {
            showResultValid(resultEmail, '인증코드가 발송 되었습니다.');
            // 인증코드 입력 필드 활성화
            auth.style.display = 'block';
        }
        isEmailOk = false;
    })


    // 이메일 인증코드 확인
    btnAuthEmail.onclick = async function () {
        const userauth = document.querySelector('.userAuth');

        console.log(userauth)
        const jsonData = {"code": userauth.value};

        const data = await fetchPost(`/user/UserRegister/email`, jsonData);

        console.log(data + data.result)

        if (!data.result) {
            showResultInvalid(resultEmail, '인증코드가 일치하지 않습니다.');
            isEmailOk = false;
        } else {
            showResultValid(resultEmail, '이메일이 인증되었습니다.');
            isEmailOk = true;
        }
    }

    // 6.휴대폰 유효성 검사
    modifyForm.userHp.addEventListener('focusout', async function () {

        isHpOk = false;
        userHp = modifyForm.userHp.value;
        const type = 'hp'
        const value = userHp;

        console.log(value);

        if (!value.match(reHp)) {
            resultHp.innerText = '전화번호가 유효하지 않습니다.';
            resultHp.style.color = 'red';
            return;
        }

        const data = await fetchGet(`/user/UserRegister/${type}/${value}`);
        console.log(data.result)

        if (data.result > 0) {
            resultHp.innerText = '이미 사용중인 휴대폰번호 입니다.';
            resultHp.style.color = 'red';
            isHpOk = false;
        } else {
            resultHp.innerText = '사용할 수 있는 번호입니다.';
            resultHp.style.color = 'green';
            isHpOk = true;
        }
    });


    //회원 정보 수정
    btn_submit.addEventListener('click', async function (e) {

        e.preventDefault();
        userZip = modifyForm.userZip.value;
        userAddr1 = modifyForm.userAddr1.value;
        userAddr2 = modifyForm.userAddr2.value;

        const jsonData = {
            "userUid": uid,
            "userName": userName,
            "userNick": userNick,
            "userEmail": userEmail,
            "userHp": userHp,
            "userZip": userZip,
            "userAddr1": userAddr1,
            "userAddr2": userAddr2,
            "userRegDate": regDate
        }

        console.log(jsonData);

        if (isFormOk()) {
            const data = await fetchPost(`/userInfo/UserMyinfo`, jsonData);
            console.log(data);
            if (data) {
                alert('회원 정보가 변경되었습니다.')
                window.location.reload();
            } else {
                alert('새로고침 후 다시 시도해 주세요.')
            }

        } else {
            alert('입력한 정보를 다시 확인해 주세요.')
        }

    });

    const quitPass = document.querySelector('.quitPass');
    const quit_result = document.querySelector('.quit_result');

    btn_quit.addEventListener('click',async function (e){

        const leavePass = quitPass.value;

        if(leavePass==null || leavePass == ''){

            alert('소셜 유저는 비밀번호 재설정 후 탈퇴하실 수 있습니다.')
            quitPass.style.display='block';
            quit_result.innerText = '비밀번호 입력 후 탈퇴 버튼을 눌러주세요.'
            console.log("console.log(leavePass) 1 "+leavePass)
            return;
        }else{

            const jsonData = {
                "userUid":uid,
                "userPass":leavePass
            }

            console.log(jsonData)
            const data = await fetchPost('/userInfo/LeavePass',jsonData);

            if(data){
                const isConfirmed = confirm('정말 탈퇴하시겠습니까?\n확인 버튼 선택시, 계정은 삭제되며 복구되지 않습니다.');
                if(isConfirmed){
                    // uid를 단순 문자열로 전송
                    fetch('/userInfo/UserMyinfoLeave', {
                        method: 'POST',  // POST 요청
                        headers: {
                            'Content-Type': 'text/plain'  // 문자열 전송임을 명시
                        },
                        body: uid  // uid만 전송
                    })
                        .then(resp => resp.json())  // 응답을 JSON으로 처리
                        .then(data => {
                            console.log(data);
                            if(data){
                                alert('회원탈퇴가 완료되었습니다.\n지금까지 FarmStory를 이용해 주신 것을 진심으로 감사드립니다.')
                                window.location.replace('/user/logout');
                            }
                        })
                        .catch(err => {
                            alert('로그인 후 다시 시도해 주세요.');
                        });
                }
            }else{
                alert('비밀번호가 맞지 않습니다.\n다시 시도해 주세요.')
            }

        }


    });
}//onload