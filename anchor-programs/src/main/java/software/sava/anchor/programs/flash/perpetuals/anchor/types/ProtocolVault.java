package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record ProtocolVault(PublicKey _address,
                            Discriminator discriminator,
                            PublicKey key,
                            PublicKey tokenAccount,
                            boolean isInitialized,
                            int bump,
                            int tokenAccountBump,
                            long feeShareBps,
                            long feeAmount,
                            long[] padding) implements Borsh {

  public static final int BYTES = 123;
  public static final int PADDING_LEN = 4;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int KEY_OFFSET = 8;
  public static final int TOKEN_ACCOUNT_OFFSET = 40;
  public static final int IS_INITIALIZED_OFFSET = 72;
  public static final int BUMP_OFFSET = 73;
  public static final int TOKEN_ACCOUNT_BUMP_OFFSET = 74;
  public static final int FEE_SHARE_BPS_OFFSET = 75;
  public static final int FEE_AMOUNT_OFFSET = 83;
  public static final int PADDING_OFFSET = 91;

  public static Filter createKeyFilter(final PublicKey key) {
    return Filter.createMemCompFilter(KEY_OFFSET, key);
  }

  public static Filter createTokenAccountFilter(final PublicKey tokenAccount) {
    return Filter.createMemCompFilter(TOKEN_ACCOUNT_OFFSET, tokenAccount);
  }

  public static Filter createIsInitializedFilter(final boolean isInitialized) {
    return Filter.createMemCompFilter(IS_INITIALIZED_OFFSET, new byte[]{(byte) (isInitialized ? 1 : 0)});
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createTokenAccountBumpFilter(final int tokenAccountBump) {
    return Filter.createMemCompFilter(TOKEN_ACCOUNT_BUMP_OFFSET, new byte[]{(byte) tokenAccountBump});
  }

  public static Filter createFeeShareBpsFilter(final long feeShareBps) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, feeShareBps);
    return Filter.createMemCompFilter(FEE_SHARE_BPS_OFFSET, _data);
  }

  public static Filter createFeeAmountFilter(final long feeAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, feeAmount);
    return Filter.createMemCompFilter(FEE_AMOUNT_OFFSET, _data);
  }

  public static ProtocolVault read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static ProtocolVault read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static ProtocolVault read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], ProtocolVault> FACTORY = ProtocolVault::read;

  public static ProtocolVault read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var key = readPubKey(_data, i);
    i += 32;
    final var tokenAccount = readPubKey(_data, i);
    i += 32;
    final var isInitialized = _data[i] == 1;
    ++i;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var tokenAccountBump = _data[i] & 0xFF;
    ++i;
    final var feeShareBps = getInt64LE(_data, i);
    i += 8;
    final var feeAmount = getInt64LE(_data, i);
    i += 8;
    final var padding = new long[4];
    Borsh.readArray(padding, _data, i);
    return new ProtocolVault(_address,
                             discriminator,
                             key,
                             tokenAccount,
                             isInitialized,
                             bump,
                             tokenAccountBump,
                             feeShareBps,
                             feeAmount,
                             padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    key.write(_data, i);
    i += 32;
    tokenAccount.write(_data, i);
    i += 32;
    _data[i] = (byte) (isInitialized ? 1 : 0);
    ++i;
    _data[i] = (byte) bump;
    ++i;
    _data[i] = (byte) tokenAccountBump;
    ++i;
    putInt64LE(_data, i, feeShareBps);
    i += 8;
    putInt64LE(_data, i, feeAmount);
    i += 8;
    i += Borsh.writeArrayChecked(padding, 4, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
