package gyul.songgyubin.daytogo.auth.model.mapper

import gyul.songgyubin.daytogo.auth.model.UserUiModel
import gyul.songgyubin.domain.auth.model.UserEntity

/**
 * User Ui Model 관련 Mapper
 *
 * @author   Gyub
 * @created  2024/02/07
 */

/**
 * Mapper
 * [UserEntity] to [UserUiModel]
 */
fun UserEntity.toUiModel(): UserUiModel {
    return UserUiModel(
        uid = this.uid.orEmpty(),
        email = this.email.orEmpty()
    )
}