package com.example.pventure.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // ==========================
    // 🔹 Common
    // ==========================
    BAD_REQUEST("CM00401", HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_INPUT_VALUE("CM00402", HttpStatus.BAD_REQUEST, "유효하지 않은 입력 값입니다."),
    UNAUTHORIZED("CM00501", HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    FORBIDDEN("CM00601", HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    NOT_FOUND("CM00701", HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다."),
    NOT_FOUND_ENDPOINT("CM00702", HttpStatus.NOT_FOUND, "엔드포인트를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED("CM00703", HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메소드입니다."),
    INTERNAL_SERVER_ERROR("CM01001", HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),

    // ==========================
    // 🔹 Member (MB)
    // ==========================
    NOT_FOUND_MEMBER("MB00701", HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."),
    DUPLICATE_MEMBER("MB00801", HttpStatus.CONFLICT, "이미 존재하는 회원입니다."),
    UNAUTHORIZED_MEMBER("MB00501", HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    UNAUTHORIZED_MEMBER_ACCESS("MB00601", HttpStatus.FORBIDDEN, "해당 여행에 권한이 없습니다."),

    // ==========================
    // 🔹 Auth (AU)
    // ==========================
    INVALID_AUTH_REQUEST("AU00401", HttpStatus.BAD_REQUEST, "잘못된 인증 요청입니다."),
    INVALID_TOKEN("AU00501", HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    FORBIDDEN_AUTH("AU00601", HttpStatus.FORBIDDEN, "권한이 없습니다."),
    DUPLICATE_ACCOUNT("AU00801", HttpStatus.CONFLICT, "이미 가입된 계정입니다."),

    // ==========================
    // 🔹 User (US)
    // ==========================
    NOT_FOUND_USER("US00701", HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    DUPLICATE_USER("US00801", HttpStatus.CONFLICT, "이미 존재하는 사용자입니다."),
    UNPROCESSABLE_USER("US00901", HttpStatus.UNPROCESSABLE_ENTITY, "사용자 정보를 처리할 수 없습니다."),

    // ==========================
    // 🔹 Place (PL)
    // ==========================
    NOT_FOUND_PLACE("PL00701", HttpStatus.NOT_FOUND, "장소를 찾을 수 없습니다."),
    INVALID_PLACE_REQUEST("PL00401", HttpStatus.BAD_REQUEST, "잘못된 장소 요청입니다."),
    DUPLICATE_PLACE("PL00801", HttpStatus.CONFLICT, "이미 등록된 장소입니다."),

    // ==========================
    // 🔹 Trip (TR)
    // ==========================
    NOT_FOUND_TRIP("TR00701", HttpStatus.NOT_FOUND, "여행 정보를 찾을 수 없습니다."),
    DUPLICATE_TRIP("TR00801", HttpStatus.CONFLICT, "여행 일정이 중복되었습니다."),
    UNPROCESSABLE_TRIP("TR00901", HttpStatus.UNPROCESSABLE_ENTITY, "여행 정보를 처리할 수 없습니다."),

    // ==========================
    // 🔹 Schedule (SC)
    // ==========================
    NOT_FOUND_SCHEDULE("SC00701", HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."),
    DUPLICATE_SCHEDULE("SC00801", HttpStatus.CONFLICT, "중복된 일정입니다."),
    INVALID_SCHEDULE_REQUEST("SC00401", HttpStatus.BAD_REQUEST, "유효하지 않은 일정 요청입니다."),

    // ==========================
    // 🔹 Album (AL)
    // ==========================
    NOT_FOUND_ALBUM("AL00701", HttpStatus.NOT_FOUND, "앨범을 찾을 수 없습니다."),
    DUPLICATE_ALBUM("AL00801", HttpStatus.CONFLICT, "이미 존재하는 앨범입니다."),
    UNPROCESSABLE_ALBUM("AL00901", HttpStatus.UNPROCESSABLE_ENTITY, "앨범을 처리할 수 없습니다."),

    // ==========================
    // 🔹 Photo (PH)
    // ==========================
    NOT_FOUND_PHOTO("PH00701", HttpStatus.NOT_FOUND, "사진을 찾을 수 없습니다."),
    DUPLICATE_PHOTO("PH00801", HttpStatus.CONFLICT, "중복된 사진입니다."),
    UNPROCESSABLE_PHOTO("PH00901", HttpStatus.UNPROCESSABLE_ENTITY, "사진을 업로드할 수 없습니다."),

    // ==========================
    // 🔹 Folder (FO)
    // ==========================
    NOT_FOUND_FOLDER("FO00701", HttpStatus.NOT_FOUND, "폴더를 찾을 수 없습니다."),
    DUPLICATE_FOLDER("FO00801", HttpStatus.CONFLICT, "기본폴더 한개만 존재해야합니다."),
    FORBIDDEN_FOLDER_ACCESS("FO00601", HttpStatus.FORBIDDEN, "폴더 접근 권한이 없습니다."),
    CANNOT_EDIT_DEFAULT_FOLDER("FO00602", HttpStatus.FORBIDDEN, "기본 폴더는 수정할 수 없습니다."),
    CANNOT_DELETE_DEFAULT_FOLDER("FO00603", HttpStatus.FORBIDDEN, "기본 폴더는 삭제할 수 없습니다."),
    TRIP_MUST_BELONG_TO_AT_LEAST_ONE_FOLDER("FO00604", HttpStatus.FORBIDDEN,"여행은 최소한 하나의 폴더에 속해야 합니다."),

    // ==========================
    // 🔹 FolderTrip (FT)
    // ==========================
    NOT_FOUND_FOLDER_TRIP("FT00701", HttpStatus.NOT_FOUND, "폴더 내 여행 정보를 찾을 수 없습니다."),
    DUPLICATE_FOLDER_TRIP("FT00801", HttpStatus.CONFLICT, "이미 폴더에 추가된 여행입니다."),
    INVALID_FOLDER_TRIP_REQUEST("FT00401", HttpStatus.BAD_REQUEST, "잘못된 폴더-여행 요청입니다."),
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    private static final Map<String, ErrorCode> BY_CODE =
            Arrays.stream(values()).collect(Collectors.toUnmodifiableMap(ErrorCode::getCode, Function.identity()));

    public static ErrorCode fromCode(String code) {
        return BY_CODE.get(code);
    }
}


