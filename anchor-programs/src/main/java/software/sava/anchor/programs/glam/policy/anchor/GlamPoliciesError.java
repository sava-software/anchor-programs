package software.sava.anchor.programs.glam.policy.anchor;

import software.sava.anchor.programs._commons.ProgramError;

public sealed interface GlamPoliciesError extends ProgramError permits
    GlamPoliciesError.InvalidSourcePolicyAccount,
    GlamPoliciesError.LockUp,
    GlamPoliciesError.NotAuthorized {

  static GlamPoliciesError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> InvalidSourcePolicyAccount.INSTANCE;
      case 6001 -> LockUp.INSTANCE;
      case 6002 -> NotAuthorized.INSTANCE;
      default -> throw new IllegalStateException("Unexpected GlamPolicies error code: " + errorCode);
    };
  }

  record InvalidSourcePolicyAccount(int code, String msg) implements GlamPoliciesError {

    public static final InvalidSourcePolicyAccount INSTANCE = new InvalidSourcePolicyAccount(
        6000, "Invalid source policy account"
    );
  }

  record LockUp(int code, String msg) implements GlamPoliciesError {

    public static final LockUp INSTANCE = new LockUp(
        6001, "Policy violation: lock-up has not expired"
    );
  }

  record NotAuthorized(int code, String msg) implements GlamPoliciesError {

    public static final NotAuthorized INSTANCE = new NotAuthorized(
        6002, "Not authorized"
    );
  }
}
