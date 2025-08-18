package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record LendingPoolBankConfigureEvent(GroupEventHeader header,
                                            PublicKey bank,
                                            PublicKey mint,
                                            BankConfigOpt config) implements Borsh {

  public static LendingPoolBankConfigureEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var header = GroupEventHeader.read(_data, i);
    i += Borsh.len(header);
    final var bank = readPubKey(_data, i);
    i += 32;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var config = BankConfigOpt.read(_data, i);
    return new LendingPoolBankConfigureEvent(header,
                                             bank,
                                             mint,
                                             config);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(header, _data, i);
    bank.write(_data, i);
    i += 32;
    mint.write(_data, i);
    i += 32;
    i += Borsh.write(config, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(header) + 32 + 32 + Borsh.len(config);
  }
}
