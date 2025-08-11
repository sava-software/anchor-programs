package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record OracleParams(PublicKey intOracleAccount,
                           PublicKey extOracleAccount,
                           OracleType oracleType,
                           long maxDivergenceBps,
                           long maxConfBps,
                           int maxPriceAgeSec,
                           int maxBackupAgeSec) implements Borsh {

  public static final int BYTES = 89;

  public static OracleParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var intOracleAccount = readPubKey(_data, i);
    i += 32;
    final var extOracleAccount = readPubKey(_data, i);
    i += 32;
    final var oracleType = OracleType.read(_data, i);
    i += Borsh.len(oracleType);
    final var maxDivergenceBps = getInt64LE(_data, i);
    i += 8;
    final var maxConfBps = getInt64LE(_data, i);
    i += 8;
    final var maxPriceAgeSec = getInt32LE(_data, i);
    i += 4;
    final var maxBackupAgeSec = getInt32LE(_data, i);
    return new OracleParams(intOracleAccount,
                            extOracleAccount,
                            oracleType,
                            maxDivergenceBps,
                            maxConfBps,
                            maxPriceAgeSec,
                            maxBackupAgeSec);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    intOracleAccount.write(_data, i);
    i += 32;
    extOracleAccount.write(_data, i);
    i += 32;
    i += Borsh.write(oracleType, _data, i);
    putInt64LE(_data, i, maxDivergenceBps);
    i += 8;
    putInt64LE(_data, i, maxConfBps);
    i += 8;
    putInt32LE(_data, i, maxPriceAgeSec);
    i += 4;
    putInt32LE(_data, i, maxBackupAgeSec);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
