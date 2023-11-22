package com.oneteam.dormeaseadmin.product;

import com.oneteam.dormeaseadmin.admin.member.MemberDto;
import com.oneteam.dormeaseadmin.admin.school.SchoolDto;
import com.oneteam.dormeaseadmin.utils.pagination.Criteria;
import com.oneteam.dormeaseadmin.utils.pagination.PageMakerDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class ProductService {

    private final IProductMapper productMapper;
    public ProductService(IProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    // 상품 등록 확인 (학교 관리자 용)
    public int registProductConfirm(ProductRegistDto productRegistDto,
                                    List<String> img,
                                    List<String> name,
                                    List<Integer> price) {
        log.info("registProductConfirm()");


        List<ProductRegistDto> productRegistDtos = new ArrayList<>();
        ProductRegistDto addData = productMapper.selectSchoolZipCodeAndName(productRegistDto);

        for(int i=0; i<name.size(); i++){

            ProductRegistDto phDto = new ProductRegistDto();
            phDto.setZip_code(addData.getZip_code());
            phDto.setSchool_name(addData.getSchool_name());
            phDto.setAdmin_id((productRegistDto.getAdmin_id()));
            phDto.setAdmin_name(productRegistDto.getAdmin_name());
            phDto.setImg(img.get(i));
            phDto.setProduct_name(name.get(i));
            phDto.setProduct_price(price.get(i));

            productRegistDtos.add(phDto);
        }

        return productMapper.registProductConfirm(productRegistDtos);
    }

    // 상품 전체 조회 (학교 관리자 용)
    public Map<String, Object> selectAllProduct() {
        log.info("selectAllProduct()");

        Map<String, Object> map = new HashMap<>();
        List<ProductDto> productDtos = productMapper.selectAllProduct();
        map.put("productDtos", productDtos);

        return map;
    }

    //상품 검색 (학교 관리자 용)
    public Map<String, Object> selectProduct(String productName) {
        log.info("selectProduct()");

        Map<String, Object> map = new HashMap<>();
        List<ProductDto> productDtos = new ArrayList<>();
        if(productName == "" || productName == null){       //전체 조회
            productDtos = productMapper.selectAllProduct();
        }
        else{
            productDtos = productMapper.selectProduct(productName);
        }

        map.put("productDtos", productDtos);

        return map;
    }

    //등록 상품 데이터 베이스 존재 여부 (학교 관리자 용)
    public Map<String, Object> isExistDatabase(String productName, MemberDto loginedMemberDto) {
        log.info("isExistDatabase()");

        Map<String, Object> item = new HashMap<>();
        ProductRegistDto productRegistDto = new ProductRegistDto();
        productRegistDto.setProduct_name(productName);

        productRegistDto.setZip_code(productMapper.selectSchoolZipCodeByAdmin(loginedMemberDto));

        int countExist = productMapper.isExistDatabase(productRegistDto);

        item.put("countExist", countExist);

        return item;
    }

    //등록 상품 리스트 (학교 관리자 용)
    public Map<String, Object> registProductList(String schoolNo, String keyWord, int pageNum, int amount) {
        log.info("registProductList()");

        Map<String, Object> list = new HashMap<>();

        Criteria criteria = new Criteria(pageNum, amount);  //Criteria(pageNum=2, amount=10, skip=10)

        String zip_code = productMapper.selectSchoolZipCodeBySchoolNo(schoolNo);    //관리자가 속한 학교의 우편번호
        ProductListDto productListDto = new ProductListDto(zip_code, criteria.getSkip(), amount); //ProductListDto(zip_code=24286 , skip=0, amount=5)
        productListDto.setKeyWord(keyWord);

        List<ProductRegistDto> productRegistDtos = productMapper.registProductList(productListDto);
        int totalCnt = productMapper.listTotalCnt(zip_code, keyWord);

        PageMakerDto pageMakerDto = new PageMakerDto(criteria, totalCnt);

        list.put("productRegistDtos", productRegistDtos);
        list.put("pageMakerDto", pageMakerDto);

        return list;
    }

    //등록 상품 해제 (학교 관리자 용)
    public int unRegistProduct(int no) {
        log.info("unRegistProduct()");

        //보여지는 리스트는 해당 학교의 결과만 보이므로
        //status 변경에는 no만 사용
        return productMapper.unRegistProductByNo(no);
    }

    //상품 중복 확인 (최종 관리자용)
    public Map<String, Object> adminAlreadyRegist(String productName) {
        log.info("adminAlreadyRegist");

        Map<String, Object> item = new HashMap<>();
        int countExist = productMapper.isAlreadyRegist(productName);
        item.put("countExist", countExist);
        return item;
    }

    //상품 등록 확인 (최종 관리자용)
    public int adminProductConfirm(ProductDto productDto) {
        log.info("adminProductConfirm()");

        return productMapper.adminProductConfirm(productDto);
    }

    //등록 상품 리스트 (최종 관리자용)
    public Map<String, Object> adminProductList(String keyWord, int pageNum, int amount) {
        log.info("adminProductList()");

        Map<String, Object> list = new HashMap<>();

        Criteria criteria = new Criteria(pageNum, amount);
        ProductListDto productListDto = new ProductListDto(criteria.getSkip(), amount);
        productListDto.setKeyWord(keyWord);

        List<ProductDto> productDtos = productMapper.adminProductList(productListDto);
        int totalCnt = productMapper.adminListTotalCnt(keyWord);
        PageMakerDto pageMakerDto = new PageMakerDto(keyWord, criteria, totalCnt);

        list.put("productDtos", productDtos);
        list.put("pageMakerDto", pageMakerDto);

        return list;
    }

    //등록 상품 해제 (최종 관리자용)
    public int unRegistProductAdmin(int no) {
        log.info("unRegistProductAdmin()");

        String productName = productMapper.searchProductNameByNo(no);
        int result = productMapper.unRegistProductByName(productName);

        return productMapper.unRegistProductAdmin(no);
    }

    // 등록 및 해제 공지사항 등록
    public int productNotice(ProductDto productDto, int num) {
        log.info("productNotice()");

        int result = 0;
        if(num == 1){       // 등록 : 1
            productDto.setStatus(num);
            result = productMapper.insertNotice(productDto);
        }
        else if(num == 0){  // 해제 : 0
            productDto = productMapper.searchProductByNo(productDto.getNo());
            productDto.setStatus(num);

            result = productMapper.insertNotice(productDto);
        }

        return result;
    }

    // 상품 공지 (전체 관리자용) => 등록 및 해제 공지사항 리스트
    public Map<String, Object> productNoticeList(String keyWord, int pageNum, int amount) {
        log.info("productNoticeList()");

        Map<String, Object> list = new HashMap<>();

        Criteria criteria = new Criteria(pageNum, amount);  //Criteria(pageNum=2, amount=10, skip=10)
        ProductListDto productListDto = new ProductListDto(criteria.getSkip(), amount);
        productListDto.setKeyWord(keyWord);

        List<ProductNoticeDto> productNoticeDtos = productMapper.productNoticeList(productListDto);
        int totalCnt = productMapper.productNoticeListCnt(keyWord);
        PageMakerDto pageMakerDto = new PageMakerDto(criteria, totalCnt);
        //PageMakerDto{startPage=1, endPage=3, prev=false, next=true, total=30, totalPage=10, criteria=Criteria(pageNum=1, amount=3, skip=0)}

        list.put("productNoticeDtos", productNoticeDtos);
        list.put("pageMakerDto", pageMakerDto);

        return list;
    }

    // 학교 이름 찾기
    public SchoolDto findSchoolName(MemberDto loginedMemberDto) {
        log.info("SchoolDto()");

        return productMapper.findSchoolByCode(loginedMemberDto);
    }
}
