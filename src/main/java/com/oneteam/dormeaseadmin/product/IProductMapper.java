package com.oneteam.dormeaseadmin.product;

import com.oneteam.dormeaseadmin.admin.member.MemberDto;
import com.oneteam.dormeaseadmin.admin.school.SchoolDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IProductMapper {

    //학교 관리자용
    public String selectSchoolZipCodeByAdmin(MemberDto loginedMemberDto);
    public String selectSchoolZipCodeBySchoolNo(String schoolNo);
    public ProductRegistDto selectSchoolZipCodeAndName(ProductRegistDto productRegistDto);
    public int registProductConfirm(List<ProductRegistDto> productRegistDtos);
    public List<ProductDto> selectAllProduct();
    public List<ProductDto> selectProduct(String productName);
    public List<ProductDto> selectProductList(List<String> names);
    public int isExistDatabase(ProductRegistDto productRegistDto);
    public List<ProductRegistDto> registProductList(ProductListDto productListDto);
    public int listTotalCnt(String zip_code, String keyWord);
    public int unRegistProductByNo(int no);

    //최종 관리자용
    public int isAlreadyRegist(String productName);
    public int adminProductConfirm(ProductDto productDto);
    public List<ProductDto> adminProductList(ProductListDto productListDto);
    public int adminListTotalCnt(String keyWord);
    public int unRegistProductAdmin(int no);
    public List<ProductDto> searchAdminProductConfirm(ProductListDto productListDto);
    public String searchProductNameByNo(int no);
    public int unRegistProductByName(String productName);
    public int insertNotice(ProductDto productDto);
    public int updateNotice(ProductDto productDto);
    public ProductDto searchProductByNo(int no);

    //전체 관리자용
    public List<ProductNoticeDto> productNoticeList(ProductListDto productListDto);
    public int productNoticeListCnt(String keyWord);

    ////-----///

    //학교 관리를 위한 추가 기능 (학교 관리자 용)
    public SchoolDto findSchoolByCode(MemberDto loginedMemberDto);
}
