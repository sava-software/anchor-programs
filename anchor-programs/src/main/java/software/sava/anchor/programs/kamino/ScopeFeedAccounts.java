package software.sava.anchor.programs.kamino;

import software.sava.core.accounts.PublicKey;

public record ScopeFeedAccounts(PublicKey oraclePrices,
                                PublicKey oracleMappings,
                                PublicKey configuration) {

  // https://github.com/Kamino-Finance/scope-sdk/blob/36b105b997bab9da6af5596507ff617d3932bee7/src/constants/index.ts#L27
  public static final ScopeFeedAccounts SCOPE_MAINNET_HUBBLE_FEED = createAccounts(
      "3NJYftD5sjVfxSnUdZ1wVML8f3aC6mp1CXCL6L7TnU8C",
      "Chpu5ZgfWX5ZzVpUx9Xvv4WPM75Xd7zPJNDPsFnCpLpk",
      "AdTiP7QyjUyv6crF4H8z7fxJKU7Z5eCAGvJN1Y55cXxb"
  );

  public static final ScopeFeedAccounts SCOPE_MAINNET_KLEND_FEED = createAccounts(
      "3t4JZcueEzTbVP6kLxXrL3VpWx45jDer4eqysweBchNH",
      "4zh6bmb77qX2CL7t5AJYCqa6YqFafbz3QJNeFvZjLowg",
      "6cMwdbrJ95D7v5655Zsoe7oXmjQJMnagWK8EcdG6qmGM"
  );

  public static ScopeFeedAccounts createAccounts(final String oraclePrices,
                                                 final String oracleMappings,
                                                 final String configuration) {
    return new ScopeFeedAccounts(
        PublicKey.fromBase58Encoded(oraclePrices),
        PublicKey.fromBase58Encoded(oracleMappings),
        PublicKey.fromBase58Encoded(configuration)
    );
  }
}
