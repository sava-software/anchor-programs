package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record FeeStateCache(PublicKey globalFeeWallet,
                            WrappedI80F48 programFeeFixed,
                            WrappedI80F48 programFeeRate,
                            long lastUpdate) implements Borsh {

  public static final int BYTES = 72;

  public static FeeStateCache read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var globalFeeWallet = readPubKey(_data, i);
    i += 32;
    final var programFeeFixed = WrappedI80F48.read(_data, i);
    i += Borsh.len(programFeeFixed);
    final var programFeeRate = WrappedI80F48.read(_data, i);
    i += Borsh.len(programFeeRate);
    final var lastUpdate = getInt64LE(_data, i);
    return new FeeStateCache(globalFeeWallet,
                             programFeeFixed,
                             programFeeRate,
                             lastUpdate);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    globalFeeWallet.write(_data, i);
    i += 32;
    i += Borsh.write(programFeeFixed, _data, i);
    i += Borsh.write(programFeeRate, _data, i);
    putInt64LE(_data, i, lastUpdate);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
