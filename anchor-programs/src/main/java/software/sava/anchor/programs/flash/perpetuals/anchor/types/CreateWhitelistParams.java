package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record CreateWhitelistParams(boolean isSwapFeeExempt,
                                    boolean isDepositFeeExempt,
                                    boolean isWithdrawalFeeExempt,
                                    PublicKey poolAddress) implements Borsh {

  public static final int BYTES = 35;

  public static CreateWhitelistParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var isSwapFeeExempt = _data[i] == 1;
    ++i;
    final var isDepositFeeExempt = _data[i] == 1;
    ++i;
    final var isWithdrawalFeeExempt = _data[i] == 1;
    ++i;
    final var poolAddress = readPubKey(_data, i);
    return new CreateWhitelistParams(isSwapFeeExempt,
                                     isDepositFeeExempt,
                                     isWithdrawalFeeExempt,
                                     poolAddress);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) (isSwapFeeExempt ? 1 : 0);
    ++i;
    _data[i] = (byte) (isDepositFeeExempt ? 1 : 0);
    ++i;
    _data[i] = (byte) (isWithdrawalFeeExempt ? 1 : 0);
    ++i;
    poolAddress.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
