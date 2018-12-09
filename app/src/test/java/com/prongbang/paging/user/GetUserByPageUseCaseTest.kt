package com.prongbang.paging.user

import androidx.lifecycle.Transformations
import com.prongbang.paging.feature.user.di.Injection
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.logging.Logger

@RunWith(JUnit4::class)
class GetUserByPageUseCaseTest {

    private val logger = Logger.getLogger("GetUserByPageUseCaseTest")

    @Test
    fun getUser_SuccessTest() = runBlocking {
        val repository = Injection.provideUserRepository()

        val result = repository.getUserByPaged(1, 10)

        logger.info("result: ${result.pagedList}")
        result.pagedList.observeForever {
            logger.info("it: $it")
        }

    }
}