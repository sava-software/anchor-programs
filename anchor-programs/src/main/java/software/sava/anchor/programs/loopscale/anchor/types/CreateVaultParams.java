package software.sava.anchor.programs.loopscale.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;

public record CreateVaultParams(String tokenName, byte[] _tokenName,
                                String tokenSymbol, byte[] _tokenSymbol,
                                String tokenUri, byte[] _tokenUri,
                                PublicKey manager,
                                CreateStrategyParams createStrategyParams) implements Borsh {

  public static CreateVaultParams createRecord(final String tokenName,
                                               final String tokenSymbol,
                                               final String tokenUri,
                                               final PublicKey manager,
                                               final CreateStrategyParams createStrategyParams) {
    return new CreateVaultParams(tokenName, tokenName.getBytes(UTF_8),
                                 tokenSymbol, tokenSymbol.getBytes(UTF_8),
                                 tokenUri, tokenUri.getBytes(UTF_8),
                                 manager,
                                 createStrategyParams);
  }

  public static CreateVaultParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var tokenName = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var tokenSymbol = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var tokenUri = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var manager = readPubKey(_data, i);
    i += 32;
    final var createStrategyParams = CreateStrategyParams.read(_data, i);
    return new CreateVaultParams(tokenName, tokenName.getBytes(UTF_8),
                                 tokenSymbol, tokenSymbol.getBytes(UTF_8),
                                 tokenUri, tokenUri.getBytes(UTF_8),
                                 manager,
                                 createStrategyParams);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_tokenName, _data, i);
    i += Borsh.writeVector(_tokenSymbol, _data, i);
    i += Borsh.writeVector(_tokenUri, _data, i);
    manager.write(_data, i);
    i += 32;
    i += Borsh.write(createStrategyParams, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_tokenName)
         + Borsh.lenVector(_tokenSymbol)
         + Borsh.lenVector(_tokenUri)
         + 32
         + Borsh.len(createStrategyParams);
  }
}
