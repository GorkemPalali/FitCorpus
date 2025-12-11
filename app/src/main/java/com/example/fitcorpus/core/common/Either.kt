package com.example.fitcorpus.core.common

sealed class Either<out L, out R> {
    data class Left<out L>(val value: L) : Either<L, Nothing>()
    data class Right<out R>(val value: R) : Either<Nothing, R>()
    
    fun isLeft(): Boolean = this is Left
    fun isRight(): Boolean = this is Right
    
    fun fold(left: (L) -> Unit, right: (R) -> Unit) {
        when (this) {
            is Left -> left(value)
            is Right -> right(value)
        }
    }
    
    fun <T> map(transform: (R) -> T): Either<L, T> {
        return when (this) {
            is Left -> this
            is Right -> Right(transform(value))
        }
    }
}

typealias ResultEither<T> = Either<AppError, T>