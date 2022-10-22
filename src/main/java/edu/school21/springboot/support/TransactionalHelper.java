package edu.school21.springboot.support;

import edu.school21.springboot.exception.CinemaRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.concurrent.Callable;

import static org.springframework.transaction.TransactionDefinition.ISOLATION_READ_COMMITTED;
import static org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRED;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionalHelper {

	private final PlatformTransactionManager transactionManager;

	public <R> R execute(Callable<R> callable) {
		final TransactionDefinition definition = createDefinition(PROPAGATION_REQUIRED, ISOLATION_READ_COMMITTED, false);
		return executeWithDefinition(callable, definition);
	}

	public void execute(Runnable runnable) {
		final TransactionDefinition definition = createDefinition(PROPAGATION_REQUIRED, ISOLATION_READ_COMMITTED, false);
		executeWithDefinition(runnable, definition);
	}

	public <R> R executeReadOnly(Callable<R> callable) {
		final TransactionDefinition definition = createDefinition(PROPAGATION_REQUIRED, ISOLATION_READ_COMMITTED, true);
		return executeWithDefinition(callable, definition);
	}

	public void executeReadOnly(Runnable runnable) {
		final TransactionDefinition definition = createDefinition(PROPAGATION_REQUIRED, ISOLATION_READ_COMMITTED, true);
		executeWithDefinition(runnable, definition);
	}

	private static TransactionDefinition createDefinition(int propagation, int isolation, boolean readOnly) {
		final DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		definition.setPropagationBehavior(propagation);
		definition.setIsolationLevel(isolation);
		definition.setReadOnly(readOnly);
		return definition;
	}

	private <R> R executeWithDefinition(Callable<R> callable, TransactionDefinition definition) {
		final TransactionStatus status = transactionManager.getTransaction(definition);
		final R result;
		try {
			result = callable.call();
		} catch (CinemaRuntimeException e) {
			safeRollback(status);
			throw e;
		} catch (Throwable t) {
			safeRollback(status);
			throw new CinemaRuntimeException("Error during run transaction", HttpStatus.INTERNAL_SERVER_ERROR.value(), t);
		}

		try {
			transactionManager.commit(status);
		} catch (Throwable t) {
			throw new CinemaRuntimeException("Error during commit transaction", HttpStatus.INTERNAL_SERVER_ERROR.value(), t);
		}

		return result;
	}

	private void executeWithDefinition(Runnable runnable, TransactionDefinition definition) {
		TransactionStatus status = transactionManager.getTransaction(definition);
		try {
			runnable.run();
		} catch (CinemaRuntimeException e) {
			safeRollback(status);
			throw e;
		} catch (Throwable t) {
			safeRollback(status);
			throw new CinemaRuntimeException("Error during run transaction", HttpStatus.INTERNAL_SERVER_ERROR.value(), t);
		}

		try {
			transactionManager.commit(status);
		} catch (Throwable t) {
			throw new CinemaRuntimeException("Error during commit transaction", HttpStatus.INTERNAL_SERVER_ERROR.value(), t);
		}
	}

	private void safeRollback(TransactionStatus status) {
		try {
			transactionManager.rollback(status);
		} catch (Exception re) {
			log.error("Rollback exception", re);
		}
	}

}
