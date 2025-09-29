package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record Referral(PublicKey _address,
                       Discriminator discriminator,
                       boolean isInitialized,
                       int bump,
                       PublicKey refererTokenStakeAccount,
                       PublicKey refererBoosterAccount,
                       long[] padding) implements Borsh {

  public static final int BYTES = 106;
  public static final int PADDING_LEN = 4;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int IS_INITIALIZED_OFFSET = 8;
  public static final int BUMP_OFFSET = 9;
  public static final int REFERER_TOKEN_STAKE_ACCOUNT_OFFSET = 10;
  public static final int REFERER_BOOSTER_ACCOUNT_OFFSET = 42;
  public static final int PADDING_OFFSET = 74;

  public static Filter createIsInitializedFilter(final boolean isInitialized) {
    return Filter.createMemCompFilter(IS_INITIALIZED_OFFSET, new byte[]{(byte) (isInitialized ? 1 : 0)});
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createRefererTokenStakeAccountFilter(final PublicKey refererTokenStakeAccount) {
    return Filter.createMemCompFilter(REFERER_TOKEN_STAKE_ACCOUNT_OFFSET, refererTokenStakeAccount);
  }

  public static Filter createRefererBoosterAccountFilter(final PublicKey refererBoosterAccount) {
    return Filter.createMemCompFilter(REFERER_BOOSTER_ACCOUNT_OFFSET, refererBoosterAccount);
  }

  public static Referral read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Referral read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Referral read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Referral> FACTORY = Referral::read;

  public static Referral read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var isInitialized = _data[i] == 1;
    ++i;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var refererTokenStakeAccount = readPubKey(_data, i);
    i += 32;
    final var refererBoosterAccount = readPubKey(_data, i);
    i += 32;
    final var padding = new long[4];
    Borsh.readArray(padding, _data, i);
    return new Referral(_address,
                        discriminator,
                        isInitialized,
                        bump,
                        refererTokenStakeAccount,
                        refererBoosterAccount,
                        padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    _data[i] = (byte) (isInitialized ? 1 : 0);
    ++i;
    _data[i] = (byte) bump;
    ++i;
    refererTokenStakeAccount.write(_data, i);
    i += 32;
    refererBoosterAccount.write(_data, i);
    i += 32;
    i += Borsh.writeArrayChecked(padding, 4, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
