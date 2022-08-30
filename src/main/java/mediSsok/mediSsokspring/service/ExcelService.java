//package mediSsok.mediSsokspring.service;
//
//import mediSsok.mediSsokspring.service.Util.ExcelUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class ExcelService {
//
//    @Autowired
//    ExcelUtil excelUtil;
//
//    public BasicResponse addExcel(MultipartFile file) {
//        BasicResponse res = new BasicResponse();
//
//        // 파일 존재하지 않는 경우
//        if (file.isEmpty()) {
//            res.setEmpty();
//            res.setMessage("Excel 파일을 선택해주세요.");
//            return res;
//        }
//
//        // 확장자 유효성 검사 -> 엑셀파일만 가능
//        //1안: tring ext = fileUtil.getExtension(file.getOriginalFilename());
//        //2안
//        String contentType = file.getFile().getContentType();
//
//        //1안: if (!ext.equals("xlsx") && !ext.equals("xls")) {
//        //2안
//        if(!contentType.equals(ConstContentType.XLSX)) {
//            res.setEmpty();
//            res.setMessage("Excel 파일을 선택해주세요.");
//            return res;
//        }
//
//        List<UserVO> listUser = new ArrayList<UserVO>();
//
//        // 엑셀의 셀데이터를 가져와서 VO에 담기
//        List<Map<String, Object>> listMap = excelUtil.getListData(file, 1, 3);
//
//        for (Map<String, Object> map : listMap) {
//            UserVO userInfo = new UserVO();
//
//            // 각 셀의 데이터를 VO에 set한다.
//            userInfo.setUserId(map.get("1").toString());
//            userInfo.setPassword(map.get("2").toString());
//            userInfo.setUserName(map.get("3").toString());
//
//            listUser.add(userInfo);
//        }
//
//        // 리스트에 담은 VO를 DB에 저장
//        for (UserVO oneUser : listUser){
//            userMapper.insertUser(oneUser);
//        }
//    }
//}
