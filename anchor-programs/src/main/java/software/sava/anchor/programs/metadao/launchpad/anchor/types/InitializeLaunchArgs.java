package software.sava.anchor.programs.metadao.launchpad.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InitializeLaunchArgs(long minimumRaiseAmount,
                                   long monthlySpendingLimitAmount,
                                   PublicKey[] monthlySpendingLimitMembers,
                                   int secondsForLaunch,
                                   String tokenName, byte[] _tokenName,
                                   String tokenSymbol, byte[] _tokenSymbol,
                                   String tokenUri, byte[] _tokenUri) implements Borsh {

  public static InitializeLaunchArgs createRecord(final long minimumRaiseAmount,
                                                  final long monthlySpendingLimitAmount,
                                                  final PublicKey[] monthlySpendingLimitMembers,
                                                  final int secondsForLaunch,
                                                  final String tokenName,
                                                  final String tokenSymbol,
                                                  final String tokenUri) {
    return new InitializeLaunchArgs(minimumRaiseAmount,
                                    monthlySpendingLimitAmount,
                                    monthlySpendingLimitMembers,
                                    secondsForLaunch,
                                    tokenName, tokenName.getBytes(UTF_8),
                                    tokenSymbol, tokenSymbol.getBytes(UTF_8),
                                    tokenUri, tokenUri.getBytes(UTF_8));
  }

  public static InitializeLaunchArgs read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var minimumRaiseAmount = getInt64LE(_data, i);
    i += 8;
    final var monthlySpendingLimitAmount = getInt64LE(_data, i);
    i += 8;
    final var monthlySpendingLimitMembers = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(monthlySpendingLimitMembers);
    final var secondsForLaunch = getInt32LE(_data, i);
    i += 4;
    final var tokenName = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var tokenSymbol = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var tokenUri = Borsh.string(_data, i);
    return new InitializeLaunchArgs(minimumRaiseAmount,
                                    monthlySpendingLimitAmount,
                                    monthlySpendingLimitMembers,
                                    secondsForLaunch,
                                    tokenName, tokenName.getBytes(UTF_8),
                                    tokenSymbol, tokenSymbol.getBytes(UTF_8),
                                    tokenUri, tokenUri.getBytes(UTF_8));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, minimumRaiseAmount);
    i += 8;
    putInt64LE(_data, i, monthlySpendingLimitAmount);
    i += 8;
    i += Borsh.writeVector(monthlySpendingLimitMembers, _data, i);
    putInt32LE(_data, i, secondsForLaunch);
    i += 4;
    i += Borsh.writeVector(_tokenName, _data, i);
    i += Borsh.writeVector(_tokenSymbol, _data, i);
    i += Borsh.writeVector(_tokenUri, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8
         + 8
         + Borsh.lenVector(monthlySpendingLimitMembers)
         + 4
         + Borsh.lenVector(_tokenName)
         + Borsh.lenVector(_tokenSymbol)
         + Borsh.lenVector(_tokenUri);
  }
}
