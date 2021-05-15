package com.nano.msc.common.service;

import com.nano.msc.common.enums.ExceptionEnum;
import com.nano.msc.common.enums.ResultCodeEnum;
import com.nano.msc.common.utils.BeanUtils;
import com.nano.msc.common.utils.ReflectUtils;
import com.nano.msc.common.utils.ValidatorUtils;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Objects;

import javax.validation.ValidationException;

import lombok.extern.slf4j.Slf4j;

/**
 * 服务CURD操作检查类
 *
 * @author nano
 * @version V1.0
 * @date 2019/5/31 16:07
 * Description:
 * 提供Service层的CRUD操作与检查工具类
 * 将会对Service层进行CRUD操作
 * 同时检查CRUD操作是否成功
 * 通过ResultDTO对象来返回数据结果
 * <p>
 * 注意，开启事务功能后，CRUD可能将不会进行，等待所有代码运行完后才会抛出异常
 * 所有事务功能的时候我们需要在外层Try catch，或者手动校验参数
 * </p>
 * <p>ResultDTO将会返回具体错误信息和错误代码</p>
 */
@Slf4j
public class BaseServiceUtils {

    private static void logError(Exception e) {
        log.error("error: {}", e.toString());
    }

    private static void logError(String message) {
        log.error("error: {}", message);
    }

    /**
     * 检查保存的结果
     * 如果保存失败，将抛出异常
     *
     * @param result     保存的结果
     * @param saveObject 待保存的对象
     * @return ResultDTO
     */
    private static CommonResult checkSaveSuccess(Object result, Object saveObject) {
        if (Objects.isNull(result)) {
            logError("Save error: object :" + saveObject.toString());
            return CommonResult.failed(ExceptionEnum.DATA_SAVE_ERROR.getMessage() + ": " + saveObject.toString());
        }
        log.info("Insert the success :{}", saveObject.toString());
        return CommonResult.success();
    }

    /**
     * 检查更新的结果
     * 如果保存失败，将抛出异常
     *
     * @param result     保存的结果
     * @param saveObject 待保存的对象
     * @return ResultDTO
     */
    private static CommonResult checkUpdateSuccess(Object result, Object saveObject) {
        if (Objects.isNull(result)) {
            logError("update error: object :" + saveObject.toString());
            return CommonResult.failed(ExceptionEnum.DATA_UPDATE_ERROR.getMessage() + ": " + saveObject.toString());
        }
        log.info("Update the success :{}", saveObject.toString());
        return CommonResult.success();
    }

    /**
     * 检查更新的结果(如果保存失败，将抛出异常)
     *
     * @param result     保存的结果
     * @param saveObject 待保存的对象
     * @return ResultDTO
     */
    static CommonResult checkDeleteSuccess(Object result, Object saveObject) {
        if (Objects.isNull(result)) {
            logError("delete error: object :" + saveObject.toString());
            return CommonResult.failed(ExceptionEnum.DATA_DELETE_ERROR.getMessage() + ": " + saveObject.toString());
        }
        log.info("delete the success :{}", saveObject.toString());
        return CommonResult.success();
    }

    /**
     * 获得数据库中存在的数据，通过传入的对象
     * 如果传入的参数拥有@Id字段的值
     * 将检查数据库是否存在该字段
     *
     * @param jpaRepository jpaRepository
     * @param t             待检查的类
     * @param <T>           泛型类型
     * @param <ID>          类的ID类型
     * @return null代表数据不存在
     */
    @SuppressWarnings("unchecked")
    private static <T, ID> T getSearchObjectFromDataBase(JpaRepository<T, ID> jpaRepository, T t) {
        // 首先获得带有@Id注解的字段的值
        ID idValue = null;
        try {
            idValue = (ID) ReflectUtils.getIdAnnotationValue(t);
        } catch (IllegalAccessException e) {
            logError("error: " + e.toString());
            return null;
        }
        // 如果带有@Id注解的字段的值为空
        if (Objects.isNull(idValue)) {
            return null;
        }
        // 如果不为空，将会通过ID检查数据是否存在于数据库中
        return jpaRepository.findById(idValue).orElse(null);
    }

    /**
     * 保存数据库对象
     * 首先通过ID字段数据检查是否存在
     * 如果不存在则进行保存
     * 如果存在则抛出异常
     *
     * @param jpaRepository jpaRepository
     * @param t             待检查的对象
     * @param <T>           泛型类型
     * @param <ID>          类的ID类型
     * @return ResultDTO
     */
    public static <T, ID> CommonResult saveObjectAndCheck(JpaRepository<T, ID> jpaRepository, T t) {
        if (Objects.isNull(t)) {
            return CommonResult.failed(ExceptionEnum.DATA_FORMAT_ERROR);
        }
        if (Objects.isNull(jpaRepository)) {
            return CommonResult.failed(ExceptionEnum.UNKNOWN_ERROR);

        }
        Map<String, String> validResult = ValidatorUtils.validate(t);
        if (Objects.nonNull(validResult) && validResult.size() != 0) {
            return CommonResult.failed(ExceptionEnum.DATA_FORMAT_ERROR + ": " + validResult);
        }

        if (Objects.nonNull(getSearchObjectFromDataBase(jpaRepository, t))) {
            logError("Data is existed, object is : " + t.toString());
            return CommonResult.failed(ExceptionEnum.DATA_EXISTED.getMessage() + ", " + t.getClass().getSimpleName() + ", Object is existed");
        }
        T result;
        try {
            result = jpaRepository.save(t);
        } catch (ValidationException e) {
            // 获取到校验错误，即数据字段可能为空或错误
            logError(e);
            return CommonResult.failed(ExceptionEnum.DATA_SAVE_ERROR.getMessage() + ", " + e.getCause().toString() + ", " + t);
        } catch (Exception e) {
            logError(e);
            return CommonResult.failed(ExceptionEnum.UNKNOWN_ERROR.getMessage());
        }
        return checkSaveSuccess(result, t);
    }

    /**
     * 批量保存对象
     *
     * @param jpaRepository jpaRepository
     * @param ts            待保存的数组
     * @param <T>           泛型类型
     * @param <ID>          类的ID类型
     * @return ResultDTO
     * @see #saveObjectAndCheck(JpaRepository, Object)
     */
    public static <T, ID> CommonResult saveObjectAndCheck(JpaRepository<T, ID> jpaRepository, T[] ts) {
        for (T t : ts) {
            CommonResult commonResult = saveObjectAndCheck(jpaRepository, t);
            if (!commonResult.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
                return commonResult;
            }
        }
        return CommonResult.success();
    }

    /**
     * 查询所有数据
     *
     * @param jpaRepository jpaRepository
     * @param page          页码
     * @param size          条数
     * @param <T>           泛型类型
     * @param <ID>          类的ID类型
     * @return ResultDTO
     */
    public static <T, ID> CommonResult listObjectAndCheck(JpaRepository<T, ID> jpaRepository, int page, int size) {
        return CommonResult.success(jpaRepository.findAll(PageRequest.of(page, size)));
    }

    /**
     * 更新数据库对象同时检查数据更新成功
     * 如果不存在数据，将抛出异常
     * 如果存在数据，将对应值更新后保存
     * 更新失败将抛出<code>CrudException</code>异常
     *
     * @param jpaRepository 待保存的对象仓库
     * @param t             存的对象
     * @return ResultDTO对象来表明错误
     */
    public static <T, ID> CommonResult updateObjectAndCheck(JpaRepository<T, ID> jpaRepository, T t) {
        if (Objects.isNull(t)) {
            return CommonResult.failed(ExceptionEnum.DATA_FORMAT_ERROR);
        }
        if (Objects.isNull(jpaRepository)) {
            return CommonResult.failed(ExceptionEnum.UNKNOWN_ERROR);
        }
        Object searchObject = getSearchObjectFromDataBase(jpaRepository, t);
        if (Objects.isNull(searchObject)) {
            return CommonResult.failed(ExceptionEnum.DATA_EXISTED, t);
        }
        BeanUtils.copyPropertiesTargetNotNull(searchObject, t);
        T result;
        try {
            result = jpaRepository.save(t);
        } catch (ValidationException e) {
            // 获取到校验错误，即数据字段可能为空或错误
            logError(e);
            return CommonResult.failed(ExceptionEnum.DATA_FORMAT_ERROR, e.toString() + "\n" + t);
        } catch (Exception e) {
            logError(e);
            return CommonResult.failed(ExceptionEnum.UNKNOWN_ERROR, e.toString() + "\n" + t);
        }
        return checkUpdateSuccess(result, t);
    }

    /**
     * 第一步检查是否存在，第二步再删除
     * 需要做同步处理
     *
     * @param jpaRepository jpaRepository
     * @param id            待删除的ID
     * @param <T>           删除类型
     * @param <ID>          待删除的ID类型
     * @return ResponseEnum
     */
    public static <T, ID> CommonResult deleteObjectByIdAndCheck(JpaRepository<T, ID> jpaRepository, ID id) {
        if (Objects.isNull(jpaRepository)) {
            return CommonResult.failed(ExceptionEnum.UNKNOWN_ERROR, id);
        }
        // existFlag为true表示数据库中存在
        // 首先检查id是否存在同时需要判断id是否符合类型
        if (Objects.nonNull(id)) {
            // 首先需要查询数据是否存在，不存在则返回错误ResponseEnum
            // 如果查询结果不为null，表示数据存在existFlag为true
            boolean existFlag = Objects.nonNull(jpaRepository.findById(id).orElse(null));
            if (!existFlag) {
                logError("id: " + id + "error: " + ExceptionEnum.DATA_NOT_EXIST.getMessage());
                return CommonResult.failed(ExceptionEnum.DATA_NOT_EXIST);
            }
            // 尝试通过id删除
            try {
                jpaRepository.deleteById(id);
            } catch (Exception e) {
                logError("id: " + id + "error: " + e.toString());
                return CommonResult.failed(ExceptionEnum.UNKNOWN_ERROR, e.toString() + "\n" + id);
            }
            // 检查是否删除成功，还存在则返回错误ResponseEnum
            // 如果查询结果不为null，表示数据存在existFlag为true
            existFlag = Objects.nonNull(jpaRepository.findById(id).orElse(null));
            if (existFlag) {
                logError("id: " + id + "error: " + ExceptionEnum.UNKNOWN_ERROR.getMessage());
                return CommonResult.failed(ExceptionEnum.UNKNOWN_ERROR, id);
            }
            log.info("delete the success, id = {}", id);
            return CommonResult.success();
        }
        logError("id is null");
        return CommonResult.failed(ExceptionEnum.DATA_FORMAT_ERROR);
    }

    /**
     * 第一步检查是否存在，第二步再删除
     * 需要做同步处理
     *
     * @param jpaRepository jpaRepository
     * @param t             待删除的ID
     * @param <T>           删除类型
     * @param <ID>          待删除的ID类型
     * @return ResultDTO
     * @see #deleteObjectByIdAndCheck(JpaRepository, Object)
     */
    @SuppressWarnings("unchecked")
    public static <T, ID> CommonResult deleteObjectAndCheck(JpaRepository<T, ID> jpaRepository, Object t) {
        ID id;
        try {
            id = (ID) ReflectUtils.getIdAnnotationValue(t);
        } catch (IllegalAccessException e) {
            logError("delete: " + t.toString() + "  exception: " + e.toString());
            return CommonResult.failed(ExceptionEnum.DATA_FORMAT_ERROR, e.toString() + "\n" + t);
        }
        return deleteObjectByIdAndCheck(jpaRepository, id);
    }
}
