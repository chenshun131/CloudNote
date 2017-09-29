package com.chenshun.test.lambda;

/**
 * User: chenshun131 <p />
 * Time: 17/9/13 21:51  <p />
 * Version: V1.0  <p />
 * Description: 函数式接口 <p />
 * <pre>
 *  _._ _..._ .-',     _.._(`))
 * '-. `     '  /-._.-'    ',/
 *    )         \            '.
 *   / _    _    |             \
 *  |  a    a    /              |
 *  \   .-.                     ;
 *   '-('' ).-'       ,'       ;
 *      '-;           |      .'
 *         \           \    /
 *         | 7  .__  _.-\   \
 *         | |  |  ``/  /`  /
 *        /,_|  |   /,_/   /
 *           /,_/      '`-'
 * </pre>
 */
@FunctionalInterface
public interface MyFun {

    Integer getValue(Integer num);

}
