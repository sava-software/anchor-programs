package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record LendingPoolBankConfigureOracleEvent(GroupEventHeader header,
                                                  PublicKey bank,
                                                  int oracleSetup,
                                                  PublicKey oracle) implements Borsh {

  public static LendingPoolBankConfigureOracleEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var header = GroupEventHeader.read(_data, i);
    i += Borsh.len(header);
    final var bank = readPubKey(_data, i);
    i += 32;
    final var oracleSetup = _data[i] & 0xFF;
    ++i;
    final var oracle = readPubKey(_data, i);
    return new LendingPoolBankConfigureOracleEvent(header,
                                                   bank,
                                                   oracleSetup,
                                                   oracle);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(header, _data, i);
    bank.write(_data, i);
    i += 32;
    _data[i] = (byte) oracleSetup;
    ++i;
    oracle.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(header) + 32 + 1 + 32;
  }
}
